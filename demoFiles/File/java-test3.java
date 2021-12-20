//Tính căn bậc 2 của 1 só:
// Nhập n
import java.util.Scanner;
public class CanBacHai {
    public static double squareRoot(int number) {
        double temp;
 
        double sr = number / 2;
 
        do {
            temp = sr;
            sr = (temp + (number / temp)) / 2;
        } while ((temp - sr) != 0);
 
        return sr;
    }
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.close();
        System.out.println("Căn bậc hai của "+ num+ " là: "+squareRoot(num));
    }
}
