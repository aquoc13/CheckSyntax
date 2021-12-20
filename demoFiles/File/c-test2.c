//số đảo ngược
//Input vào 1 số có ít nhát 2 chữ só

#include "stdio.h"
 
int main()
{
int n, reverse = 0, rem;
printf("Nhap vao so nguyen: ");
scanf("%d", &n);
while(n > 0)
{
rem = n % 10;
reverse = reverse*10 + rem;
n /= 10;
}
printf("So dao nguoc = %d", reverse);
return 0;
}