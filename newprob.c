#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int half_prob()
{
	  if(rand() % 2 == 0)
	  	 return 1;
	  else
	  	 return 0;
}

int* number_to_binary(int number, int n)
{
	  int *digits, i;
	  digits = (int*)malloc(sizeof(int)*n);
	  
	  for(i=0; i< n; i++)
	  {
	  	 int  curpow = (int)pow(2, n-i-1);
	  	 if(number >= curpow)
	  	 {
	  	 	  digits[i] = 1;
	  	 		number-= curpow;
	  	 }
	  	 else
	  	 	  digits[i] = 0;
	  }
	  return digits;
}

int newprob(float value)
{
	 int i =0, j=0;
	 int divlevel = 30;
	 double div[30], tmpvalue = value;
	 int digits[30];
	 for(i=0; i<divlevel; i++)
	    div[i] = pow(2.0, (double)(-1*(i+1))); 
	 //divide value into a bit form: e.g. 0.75=110000  0.625=101000		
	 for(i=0; i<divlevel; i++)
	 {
	 	  if(tmpvalue >= div[i])
	    {
	 		   digits[i] = 1;
				 tmpvalue-=div[i];
	    }
	 		else
	 	    digits[i] = 0;	 
	 }
	 for(j=0; j< divlevel; j++)
	 {
		   if(digits[j] == 1 && half_prob() == digits[j])
		 	   return 1;
		 	 if(digits[j] == 0 && half_prob()!=digits[j])
		 	 	 return 0;
	 }
	 return 0;
}

int main(int argc, char* argv[])
{
	  int i, testnum = atoi(argv[2]);
	  int c1 = 0, c2=0;
	  double ratio;
	  for(i=0; i< testnum; i++)
	  {
	  	  if(half_prob())
	  	  	  c1++;
	  	  else
	  	  	  c2++;
	  }
	  ratio = (double)c1/(double)(c1+c2);
	  printf ("%f \n", ratio);
	  c1=0; c2=0;
	  for(i=0; i< testnum; i++)
	  {
	  	  if(newprob(atof(argv[1])))
	  	  	  c1++;
	  	  else
	  	  	  c2++;
	  }
	  ratio = (double)c1/(double)(c1+c2);
	  printf ("%f \n", ratio);
	  return 1;
}
