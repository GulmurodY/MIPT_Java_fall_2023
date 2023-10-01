import java.util.Arrays;
import java.util.Scanner;

public class StringPatternSearch {

    public static int[] computePrefixFunction(String pattern) {
        int[] prefixFunction = new int[pattern.length()];
        int j = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = prefixFunction[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            prefixFunction[i] = j;
        }
        return prefixFunction;
    }

    public static int[] computeZFunction(String pattern) {
        int[] zFunction = new int[pattern.length()];
        int left = 0;
        int right = 0;
        for (int i = 1; i < pattern.length(); i++) {
            if (i > right) {
                left = i;
                right = i;
                while (right < pattern.length() && pattern.charAt(right) == pattern.charAt(right - left)) {
                    right++;
                }
                zFunction[i] = right - left;
            } else if (zFunction[i - left] < right - i) {
                zFunction[i] = zFunction[i - left];
            } else {
                left = i;
                while (right < pattern.length() && pattern.charAt(right) == pattern.charAt(right - left)) {
                    right++;
                }
                zFunction[i] = right - left;
            }
        }
        return zFunction;
    }

    public static int[] findPatternOccurrences(String pattern, String text) {
        int[] prefixFunction = computePrefixFunction(pattern);
        int[] zFunction = computeZFunction(pattern);

        int[] occurrences = new int[text.length()];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else if (j > 0) {
                j = prefixFunction[j - 1];
            } else {
                i++;
            }

            if (j == pattern.length()) {
                occurrences[k++] = i - pattern.length();
                j = prefixFunction[j - 1];
            }
        }

        return Arrays.copyOfRange(occurrences, 0, k);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String pattern = scanner.nextLine();
        String text = scanner.nextLine();

        int[] occurrences = findPatternOccurrences(pattern, text);

        StringBuilder output = new StringBuilder();
        for (int occurrence : occurrences) {
            output.append(occurrence).append(" ");
        }

        System.out.println(output.toString().trim());
    }
}
//https://contest.yandex.ru/contest/51733/run-report/91805709/
