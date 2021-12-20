//In các số nguyên tố nhỏ hơn N
//Input vào 1 số

#include "stdio.h"
 
int so_nguyen_to(int N);
int main()
{
int N, i;
 
do
{
scanf("%d", &N);
}
while(N <= 0);
printf("\n Cac so nguyen to nho hon %d :", N);
for(i = 1; i <= N; i++)
{
if(so_nguyen_to(i))
printf(" %d ", i);
}
return 0;
}
 
// Ham kiem tra so nguyen to
int so_nguyen_to(int N)
{
int i;
if(N == 1)
return 1;
else
{
for( i = 2; i < N; i++)
{
if(N % i == 0)
return 0;
}
return 1;
}
}