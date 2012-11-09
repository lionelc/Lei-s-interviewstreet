import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class flowers {
	
	public static int fnum;
	public static int pnum;
	public static PriorityQueue<Integer> priceheap; 
	public static RevComparator revcomp;
	
	static class RevComparator implements Comparator<Integer> {
		public int compare(Integer int1, Integer int2) {
			 if(int1 > int2)
				 return -1;
			 else if(int1 < int2)
				 return 1;
			 else
				 return 0;
		}
	}
	
	public static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
		revcomp = new RevComparator();
		priceheap = new PriorityQueue<Integer>(10, revcomp);
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			int count = 0;
			while((line = input.readLine()) != null)
			{
				String [] strsplit;
				strsplit = line.split(" ");
				if(count == 0)
				{
					fnum = Integer.parseInt(strsplit[0]);
					pnum = Integer.parseInt(strsplit[1]);	
				}
				else
				{
					for(int i=0; i< fnum; i++)
					{
						Integer tmpnum = Integer.parseInt(strsplit[i]);
						priceheap.offer(tmpnum);
					}
				}
				count++;
			}
		}
		finally {
			input.close();
		}
	}
	
	public static int nextP(int tmp)
	{
		if(tmp+1 >= pnum)
			return 0;
		else
			return tmp+1;
	}
	
	public static long findPrice()
	{
		long count = 0;
		int curp = 0;
		ArrayList<Integer> pfnum = new ArrayList<Integer>();
		for(int i=0; i< pnum; i++)
			pfnum.add(0);
		while(!priceheap.isEmpty())
		{
			Integer curf = priceheap.poll();
			count += curf*(pfnum.get(curp)+1);
			pfnum.set(curp, pfnum.get(curp)+1);
			curp = nextP(curp);
		}
		return count;
	}
	
	
	public static void main(String[] args)
	{
	    try {
			readFile("Flowers_testcases\\input01.txt");
			System.out.println(findPrice());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

