#include <stdio.h>
#include <stdlib.h>

#define STACKSIZE 20

class stack 
{
	public:
		stack()
		{
	       data = new int[stacksize];
		   pivot = 0;	
		   stacksize = STACKSIZE;
		}
		~stack()
		{
			delete data;
			pivot = 0;
		}

	public:
	    int *data;
		int stacksize;
		int pivot;
		
	public:
		int push(int value)
		{
			if(pivot >= stacksize)
				return -1;
			else {
				data[pivot] = value;
				pivot++;
			}
			return pivot;
		}
		
		int pop()
		{
			if(pivot == 0)
				return -1;
			pivot--;
			return data[pivot];
		}
		int printstack()
		{
			for(int i=0; i< pivot; i++)
			{
				if(i>0)
					printf(",");
				printf("%d", data[i]);
			}
			printf("\n");
			return pivot;
		}
};

int getParent(int pos)
{
	if(pos == 1)
		return -1;
	return pos/2;
}

int getFirstChild(int pos)
{
	return pos*2;
}

int getSecondChild(int pos)
{
	return pos*2+1;
}

class newstack: public stack
{
	public:
		newstack()
		{
			heappos = new int [stacksize+1];
			for(int i=1; i< stacksize+1; i++)
			{
				heappos[i] = 0;
			}
			heappos[0] = -1;
			lastheappos = -1;
			minvalue = 999999;
		}
		~newstack()
		{
			delete heappos;
		}
		int heapswap(int startpos)
		{
			int heappivot = startpos;
			while(data[heappos[heappivot]] < data[heappos[getParent(heappivot)]]) 
			{
					//swap along the path to root
					int tmppos = heappos[heappivot];
					heappos[heappivot] = heappos[getParent(heappivot)];
					heappos[getParent(heappivot)] = tmppos;
					heappivot = getParent(heappivot);
					if(heappivot == 1)
						break;
			}
			return heappivot;
		}
		
		virtual int push(int value)
		{
			if(stack::push(value) < 0)
				return -1;
			if(pivot <= 1)
			{
				heappos[1] = 0;
				lastheappos = 1;
			}
			else {
				heappos[pivot] = pivot-1;
				lastheappos = heapswap(pivot);
			}
			minvalue = data[heappos[1]];
		}
		virtual int pop()
		{
			int tmpnum = stack::pop();
			if(tmpnum < 0)
				return -1;
			heappos[lastheappos] = pivot-1;
			lastheappos = heapswap(lastheappos);
			minvalue = data[heappos[1]];
			return tmpnum;
		}
		int getMin()
		{
			return minvalue;
		}
		void printMin()
		{
			printf("min=%d size=%d \n", minvalue, pivot);
		}
	private:
		int minvalue;
		int lastheappos;
	private:
		int *heappos;
};


int main(int argc, char* argv[])
{
	newstack s;
	s.push(2);
	s.printMin();
	s.push(5);
	s.printMin();
	s.push(1);
	s.printMin();
	s.push(4);
	s.pop();
	s.printMin();
	s.pop();
	s.printMin();
	s.push(1);
	s.printMin();
	s.push(0);
	s.printMin();
	s.printstack();
}
