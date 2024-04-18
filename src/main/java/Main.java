import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger count3 = new AtomicInteger(0);
    static AtomicInteger count4 = new AtomicInteger(0);
    static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (palindromeTest(text)) {
                    switch (text.length()) {
                        case 3 -> {
                            count3.addAndGet(1);
                        }
                        case 4 -> {
                            count4.addAndGet(1);
                        }
                        case 5 -> {
                            count5.addAndGet(1);
                        }
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (oneCharTest(text)) {
                    switch (text.length()) {
                        case 3 -> {
                            count3.addAndGet(1);
                        }
                        case 4 -> {
                            count4.addAndGet(1);
                        }
                        case 5 -> {
                            count5.addAndGet(1);
                        }
                    }
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (sortCharTest(text)) {
                    switch (text.length()) {
                        case 3 -> {
                            count3.addAndGet(1);
                        }
                        case 4 -> {
                            count4.addAndGet(1);
                        }
                        case 5 -> {
                            count5.addAndGet(1);
                        }
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.printf(
                "Красивых слов с длиной 3: %d шт\n" +
                        "Красивых слов с длиной 4: %d шт\n" +
                        "Красивых слов с длиной 5: %d шт\n", count3.get(), count4.get(), count5.get()
        );
    }

    public static boolean palindromeTest(String word) {
        StringBuilder reverse = new StringBuilder();
        for (int i = word.length() - 1; i >= 0; --i) {
            reverse.append(word.charAt(i));
        }

        return word.equals(reverse.toString());
    }

    public static boolean oneCharTest(String word) {
        char previousChar = word.toCharArray()[0];
        for (char c : word.toCharArray()) {
            if (previousChar != c) {
                return false;
            }
            previousChar = c;
        }
        return true;
    }

    public static boolean sortCharTest(String word) {
        char previousChar = word.toCharArray()[0];
        for (char c : word.toCharArray()) {
            if (previousChar > c) {
                return false;
            }
            previousChar = c;
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
