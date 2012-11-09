import java.io.*;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;


public class exchange {
	
	public static int elemnum = 0;
	public static ArrayList<Integer> perm;
	
	public static ArrayList<HashSet<Integer>> connsets;
	public static ArrayList<Integer> setpos;
	public static HashSet<Integer> setnums;
	
	static void init()
	{
		perm = new ArrayList<Integer>();
		setpos = new ArrayList<Integer>();
		connsets = new ArrayList<HashSet<Integer>>();
		setnums = new HashSet<Integer>();
	}
	
	static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
		
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			int count = 0;
			while((line = input.readLine()) != null)
			{
				if(count == 0)
				{
					elemnum = Integer.parseInt(line);
					for(int i=0; i< elemnum; i++)
						setpos.add(-1);
				}
				else if(count == 1)
				{
					String [] strsplit;
					strsplit = line.split(" ");
					for(int i=0; i< elemnum; i++)
					{
						int tmpa = Integer.parseInt(strsplit[i]);
						perm.add(tmpa);
					}
				}
				else
				{
					String [] strsplit;
					strsplit = line.split("");
					for(int i=1; i< strsplit.length; i++)
					{
						if(strsplit[i].compareTo("Y") == 0)
						{
							if(setpos.get(count-2) < 0)
							{
								//see if i-1 is in a set, if not, init a new set
								if(setpos.get(i-1) < 0)
								{
									HashSet<Integer> newset = new HashSet<Integer>();
									newset.add(count-2);
									newset.add(i-1);
									connsets.add(newset);
									int setnum = connsets.size()-1;
									setpos.set(count-2, setnum);
									setpos.set(i-1, setnum);
									setnums.add(connsets.size()-1);
								}
								else
								{
									HashSet<Integer> tmpset = connsets.get(setpos.get(i-1));
									tmpset.add(count-2);
									setpos.set(count-2, setpos.get(i-1));
								}
							}
							else
							{
								if(setpos.get(i-1) < 0)
								{
									HashSet<Integer> tmpset = connsets.get(setpos.get(count-2));
									tmpset.add(i-1);
									setpos.set(i-1, setpos.get(count-2));
								}
								else if(setpos.get(i-1) != setpos.get(count-2))//combine two sets
								{
									HashSet<Integer> tmpset1 = connsets.get(setpos.get(count-2));
									HashSet<Integer> tmpset2 = connsets.get(setpos.get(i-1));
									tmpset1.addAll(tmpset2);
									setnums.remove(setpos.get(i-1));
									//don't delete tmpset2, just change setpos
									for(Integer tmppos: tmpset2)
										setpos.set(tmppos, setpos.get(count-2));
								}
							}
						}
					}
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
	
	static void sortConnElems()
	{
		Integer num = 0;
		for(HashSet<Integer> tmpset: connsets)	
		{
			if(!setnums.contains(num))
			{
				num++;
				continue;
			}
			//collect all the elems in the corresponding pos
			int subsize = tmpset.size();
			int[] elemsubset = new int[subsize];
			int[] possubset = new int[subsize];
			int count = 0;
			for(Integer i: tmpset)
			{
				elemsubset[count] = perm.get(i);
				possubset[count] = i;
				count++;
			}
			Arrays.sort(elemsubset);
			Arrays.sort(possubset);
			for(int i=0; i< subsize; i++)
			{
				perm.set(possubset[i], elemsubset[i]);
			}
			num++;
		}
	}
	
	public static void main(String[] args)
	{
	    try {
	    	init();
			readFile("Exchange_testcases\\input01.txt");
			//print the sets -- for debugging only
			/*System.out.printf("setnums: ");
			for(Integer i: setnums)
				System.out.printf("%d ", i);
			System.out.printf("\n");
			int count = 0;
			for(HashSet<Integer> tmpset: connsets)
			{
				System.out.printf("set %d : ", count);
				for(Integer tmppos: tmpset)
					System.out.printf("%d ", tmppos);
				System.out.printf("\n");
				count++;
			}*/
			sortConnElems();
			for(int i=0; i< elemnum; i++)
			{
				System.out.printf("%d", perm.get(i));
				if(i < elemnum-1)
					System.out.printf(" ");
				else
					System.out.printf("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
