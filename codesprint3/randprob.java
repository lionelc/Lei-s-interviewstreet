import java.io.*;
import java.util.*;

public class randprob {
	
	public static int casenum;
	public static ArrayList<randgen> allcases;
	
	public static class randgen
	{
		public int a;
		public int b;
		public int sum;
		
		randgen(int tmpa, int tmpb, int tmpsum)
		{
			a = tmpa;
			b = tmpb;
			sum = tmpsum;
		}
	}
	
	public static class fraction
	{
		public int numr;
		public int demr;
		
		public fraction(int num1, int num2)
		{
			numr = num1;
			demr = num2;
		}
		
		public static int gcd(int num1, int num2)
		{
			if(num1 == 0 || num2 == 0)
			{
				return 0;
			}
			int tmpgcd = 1;
			int tmpmax = 0, tmpmin = 0;
			if(num2 >= num1)
			{
				tmpmax = num2;
				tmpmin = num1;
			}
			else
			{
				tmpmax = num1;
				tmpmin = num2;
			}
			
			if(tmpmax <= 1 && tmpmin <= 1)
			{
				tmpgcd = 1;
			}
			else if(tmpmax % tmpmin == 0)
			{
				tmpgcd = tmpmin;
			}
			else
			{
				tmpgcd = gcd(tmpmax % tmpmin, tmpmin);
			}
			return tmpgcd;
		}
		
		public void degcd()
		{
			int thisgcd = gcd(numr, demr);
			if(thisgcd > 1)
			{
				numr /= thisgcd;
				demr /= thisgcd;
			}
		}
		
		public void print()
		{
			if(demr == 0)
				System.out.printf("zero denominator! \n");
			System.out.printf("%d/%d\n", numr, demr);
		}
	}
	
	static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
		allcases = new ArrayList<randgen>();
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			int count = 0;
			while((line = input.readLine()) != null)
			{
				if(count == 0)
				{
					casenum = Integer.parseInt(line);
				}
				else
				{
					String [] strsplit;
					strsplit = line.split(" ");
					int tmpa = Integer.parseInt(strsplit[0]);
					int tmpb = Integer.parseInt(strsplit[1]);
					int tmpsum = Integer.parseInt(strsplit[2]);
					randgen tmps = new randgen(tmpa, tmpb, tmpsum);
					allcases.add(tmps);
				}
				count++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			input.close();
		}
	}
	
	public static fraction findProb(randgen newcase)
	{
		int tmpmin = newcase.a, tmpmax = newcase.b;
		int allodds = newcase.b*newcase.a*2;
		int needodds = 0;
		if(newcase.b < newcase.a)
		{
			tmpmin = newcase.b;
			tmpmax = newcase.a;
		}
		for(int i=0; i< tmpmin ; i++)
		{
			int target = newcase.sum - i;
			if(target <= 0)
				break;
			if(target > tmpmax)
			{
				needodds += tmpmax*2;
			}
			else if(target == tmpmax)
			{
				needodds += tmpmax*2-1;
			}
			else
			{
				needodds += target*2-1;
			}
		}
		return new fraction(needodds, allodds);
	}
	
	
	public static void main(String[] args)
	{
	    try {
			readFile("Random-number-generator_testcases\\input01.txt");
			for(int i=0; i< casenum; i++)
			{
				fraction tmpfra = findProb(allcases.get(i));
				tmpfra.degcd();
				tmpfra.print();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
