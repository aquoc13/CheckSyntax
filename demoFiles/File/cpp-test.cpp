//Tính toán tổng, hiệu, thương và tích của 2 số cho trước

#include <iostream>

using namespace std; 
int main()
{
  
  int x = 10;
  int y = 2;
  int tong, hieu, tich, thuong;
  tong = x + y;
  hieu = x - y;
  tich = x * y;
  thuong = x / y;
  cout << "Tong cua " << x << " & " << y << " la " << tong << "." << endl;
  cout << "Hieu cua " << x << " & " << y << " la " << hieu << "." << endl;
  cout << "Tich cua " << x << " & " << y << " la " << tich << "." << endl;
  cout << "Thuong cua " << x << " & " << y << " la " << thuong << "." << endl;
  return 0;
}