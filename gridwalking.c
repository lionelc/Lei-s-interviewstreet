/* author: Lei Jiang lionelchange@gmail.com */

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

#include <stdio.h>

#define MAXSTEP 305
#define MAXDIM 15
#define MAXGRID 101
#define BIGMOD 1000000007L

long int ***status;
int *gridnum;
int *curpos;
long long int **combarray; 
long long int ***movenums;
long long int **allmoves;

void calculate1dstatus(int dim, int gridnum, int step)
{
	int i,j;

	for (i=0; i< gridnum; i++) {
		if(gridnum == 1)
			status[dim][i][0] = 0L;
		else
			status[dim][i][0] = 1L;
		if(gridnum > 1)
		{
			if (i == gridnum-1 || i == 0)
				status[dim][i][1] = 1L;
			else
				status[dim][i][1] = 2L;
		}
	}	
	for(i=2; i<= step; i++)
	{
		for(j=0; j< gridnum; j++)
		{
			if(gridnum == 1)
				 status[dim][j][i] = 0L;
			else if(j == gridnum-1)
				status[dim][j][i] = status[dim][j-1][i-1];
			else if(j == 0)
				status[dim][j][i] = status[dim][j+1][i-1];
			else
				status[dim][j][i] = (status[dim][j-1][i-1]+status[dim][j+1][i-1])% BIGMOD;
		}
	}
	//printf("status precalculated: dim=%d gridnum=%d step=%d\n", dim, gridnum, step);
}

void compComb(int step1, int step2)
{
	 int i, j;
	 for(j = step1; j<= step2; j++)
	 {
	 		 for(i = 2; i < j-1; i++)
	 		 {
	 		 		 combarray[i][j] = combarray[i-1][j]*(j-i+1);
	 		 		 combarray[i][j] = combarray[i][j] % BIGMOD;
	 		 		 int tmpmod = combarray[i][j] % i;
	 		 		 int tmpcount = 0;
	 		 		 while( tmpmod != 0)
	 		 		 {
	 		 		 		combarray[i][j] += BIGMOD;
	 		 		 		tmpmod = combarray[i][j] % i;
	 		 		 		tmpcount++;
	 		 		 }
	 		 		 if(tmpcount > step2+1)
	 		 		 {
	 		 		 	 printf("wrong results! %d %d \n", combarray[i][j], i);	
	 		 		 	 exit(0);
	 		 		 }
	 		 		 combarray[i][j] /= i;
	 		 		 combarray[i][j] = combarray[i][j] % BIGMOD;
	 		 }
	 }
}

long int moveways1d(int dim, int pos, int step)
{
	return status[dim][pos][step];
}


long long int comb(int small, int big)
{
	  if(small > big)
	  	 return 0L;
	  else if(small == big)
	  	 return 1L;
	  if(combarray[small][big] > 0)
	  	 return combarray[small][big];
	  else
	  {
	  		printf("uncalculated!!! %d %d \n", small, big);
	  		exit(0);
	  }
}

long long int moveways(int thisstep, int allsteps,  int dimnum, int alldimnum)
{
	int i,j;
	long long int sum = 0;
	
	if(thisstep == 0 || allsteps == 0)
		 return 1L;
	
	if(dimnum == alldimnum-1)
	{
		if(allsteps != thisstep)
	  {
	  	 printf("steps are wrong here!  %d %d\n", allsteps, thisstep);
	  	 exit(0);
	  }
		return moveways1d(dimnum, curpos[dimnum]-1, allsteps);
	}
	else 
	{
		  sum = moveways1d(dimnum, curpos[dimnum]-1, thisstep);
		  //need to get C_allsteps^thisstep
		  sum *= comb(thisstep, allsteps);
		  sum = sum % BIGMOD;  
	}

	return sum;
}

long long int allmoveways(int allsteps, int curdimnum, int alldimnum)
{
	 long long int allsum = 0, sum = 0;
	 int i;
	 if(allsteps <= 0)
	 	  return 1L;
	 if(curdimnum >= alldimnum-1)
	 {
	 	   return movenums[curdimnum][allsteps][allsteps];	
	 }
	 for(i =0; i <= allsteps; i++)
	 {
	 	   sum = movenums[curdimnum][allsteps][i];
	 	   sum = sum % BIGMOD;
	 	   if(i < allsteps && curdimnum < alldimnum-1)
	 	   {
	 	   	   sum *= allmoves[curdimnum+1][allsteps-i];
	 	   	   sum = sum % BIGMOD;	
	 	   }
	 	   allsum += sum;
	 	   allsum = allsum % BIGMOD;
	 }
	 return allsum;
}

void compMoveways(int allsteps, int alldimnum)
{
	 int i, j, k;
	 
	 for(i=0; i< alldimnum; i++)
	 {
	 	 for(j=0; j <= allsteps; j++)
	 	 {
	 	    for(k =j; k <= allsteps; k++)
	 	 		{
	 	 	  		if(i == alldimnum-1 && k != j)
	 	 	 				break;
	 	    		movenums[i][k][j] = moveways(j,k, i, alldimnum);
	 	 		}
	 	 }
	 }
}

void compAllmoves(int allsteps, int alldimnum)
{ 
	 int i, j;
   //then calculate allmoves
	 for(i = alldimnum-1; i>=0; i--)
	 {
	 	  for(j=0; j< allsteps+1; j++)
	 			allmoves[i][j] = allmoveways(j, i, alldimnum);
	 }
}

int main()
{
	int cnum, casenum, i, j, k;
	FILE* f = fopen("./input_grid/input06.txt", "r");
	fscanf(f, "%d\n", &cnum);
	
	combarray = (long long int**)malloc(sizeof(long long int*)*(MAXSTEP+1));
	for(i=0; i< MAXSTEP+1; i++)
		combarray[i] = (long long int*)malloc(sizeof(long long int)*(MAXSTEP+1));
	
	for(i=0; i < MAXSTEP+1; i++)
	{
		for(j=0; j< MAXSTEP+1; j++)
		{
				if(i ==0 || i==j)
					 combarray[i][j] = 1;
			  else if(i == 1 || i == j-1)
			  	 combarray[i][j] = j;
			  else if(i > j)
					 combarray[i][j] = -1;	
				else
					  combarray[i][j] = 0;
		}
	}	
	
	compComb(2, MAXSTEP);
			
	for(casenum=0; casenum < cnum; casenum++)
	{
		int dimnum, stepnum;
		fscanf(f, "%d %d\n", &dimnum, &stepnum);
		status = (long int***)malloc(sizeof(long int**)*dimnum);
		movenums = (long long int***)malloc(sizeof(long long int**)*dimnum);
		gridnum = (int*)malloc(sizeof(int)*MAXDIM);
		curpos = (int*)malloc(sizeof(int)*MAXDIM);
		allmoves = (long long int**)malloc(sizeof(long long int*)*dimnum);
		for(i=0; i< dimnum; i++)
			 allmoves[i] = (long long int*)malloc(sizeof(long long int)*(MAXSTEP+1));
		//initialization
		for(i=0; i< dimnum; i++)
		{
			if(i< dimnum-1)
				fscanf(f,"%d ", &curpos[i]);
			else
				fscanf(f, "%d\n", &curpos[i]);
			if(curpos[i] <= 0)
				printf("read error in curpos! \n");
		}
		for(i=0; i< dimnum; i++)
		{
				if(i< dimnum-1)
					fscanf(f,"%d ", &gridnum[i]);
				else
					fscanf(f, "%d\n", &gridnum[i]);
				if(gridnum[i] <= 0)
					printf("read error in gridnum! \n");
		}
		
		for(i=0; i< dimnum; i++)
		{
			status[i] = (long int**)malloc(sizeof(long int*)*MAXGRID);
			for(j=0; j< MAXGRID; j++)
			{
				status[i][j] = (long int*)malloc(sizeof(long int)*(MAXSTEP+1));
			}
			calculate1dstatus(i, gridnum[i], stepnum);
		}
		
			
		for(i=0; i< dimnum; i++)
	 	{
	   movenums[i] = (long long int**)malloc(sizeof(long long int*)*(MAXSTEP+1)); 
	   for(j=0; j< MAXSTEP+1; j++)
	      movenums[i][j] = (long long int*)malloc(sizeof(long long int)*(MAXSTEP+1));
	  }
			
		compMoveways(stepnum, dimnum);
		
		compAllmoves(stepnum, dimnum);
		
		//ok, now we can start to divide the step to each dimension
		long long int result = allmoveways(stepnum, 0, dimnum);
		printf("%ld\n", result);
		for(i=0; i< dimnum; i++)
		{
			for(j=0; j< MAXGRID; j++)
				free(status[i][j]);
			free(status[i]);
			for(j=0; j< MAXSTEP+1; j++)
				free(movenums[i][j]);
			free(movenums[i]);
			free(allmoves[i]);
		}
		free(gridnum);
		free(curpos);
		free(status);
		free(movenums);
		free(allmoves);
	}
	for(i=0; i< MAXSTEP+1; i++)
		free(combarray[i]);
	free(combarray);
	fclose(f);
}
