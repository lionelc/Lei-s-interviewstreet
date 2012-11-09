import java.io.*;
import java.util.HashSet;

public class pairs {
	
	public static int num;
	public static Long diff;
	public static HashSet<Long> allset;
	
	public static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
		allset = new HashSet<Long>();
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
					num = Integer.parseInt(strsplit[0]);
					diff = Long.parseLong(strsplit[1]);	
				}
				else
				{
					for(int i=0; i< num; i++)
					{
						Long tmpnum = Long.parseLong(strsplit[i]);
						allset.add(tmpnum);
					}
				}
				count++;
			}
		}
		finally {
			input.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static int findPairs()
	{
		int count = 0;
		HashSet<Long> copyset = (HashSet<Long>) allset.clone();
		
		for(Long tmpcomp: allset)
		{
			Long comp1 = tmpcomp + diff;
			Long comp2 = tmpcomp - diff;
			
			if(copyset.contains(comp1))
			{
				count++;
			}
			if(copyset.contains(comp2))
				count++;
			copyset.remove(tmpcomp);
		}
		return count;
	}
	
	
	public static void main(String[] args)
	{
	    try {
			readFile("Pairs_testcases\\input03.txt");
			System.out.println(findPairs());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
