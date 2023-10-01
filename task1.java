import java.util.Scanner;

public class Main
{
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        if(a < b) {
            int c = a;
            a = b;
            b = c;
        }
        while (b != 0) {
            int c = b;
            b = a % b;
            a = c;
        }
        System.out.println(a);
        scanner.close();
	}
}
//https://contest.yandex.ru/contest/51733/run-report/91411769/
