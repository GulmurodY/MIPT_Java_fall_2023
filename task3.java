import java.util.Arrays;
import java.util.Scanner;

public class Main {

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
    public static int[] findPatternOccurrences(String pattern, String text) {
        int[] prefixFunction = computePrefixFunction(pattern);
        int[] occurrences = new int[text.length()];
        
        int i = 0, j = 0, k = 0;
        
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
    private static void printOcurrence(int[] occurrences) {
        StringBuilder output = new StringBuilder();
        for (int occurrence : occurrences) {
            output.append(occurrence).append(" ");
        }
        System.out.println(output.toString().trim());
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();
        String text = scanner.nextLine();
        printOcurrence(findPatternOccurrences(pattern, text));
    }
}
//https://contest.yandex.ru/contest/51733/run-report/91805709/
