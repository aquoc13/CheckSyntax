//Viết chương trình cho phép nhập vào một số nguyên dương n, 
//tính tổng tất cả số chẵn trong khoảng từ 0 - n.
//input vào 1 số

import java.util.Scanner;

public class BaiTapJavaCoBan6 {
   public static void main(String[] args)
   {
      int n;
      int sum = 0;
      System.out.println("Nhập vào số nguyên:");
      Scanner sc = new Scanner(System.in);n = sc.nextInt();

      for (int i = 0; i <= n; i++) // duyệt tất cả phần tử từ 0-n
      if (i % 2 == 0) // nếu nó là số chẵn
         sum += i; // Cộng vào tổng.
         System.out.println(sum);
   }
}
