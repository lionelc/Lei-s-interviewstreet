import java.io.*;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class power1 {
	
	public static int casenum = 0;
	
	static class juncedge
	{
		public int junc1;
		public int junc2;
		public int cost;
		
		juncedge(int tmp1, int tmp2, int tmp3)
		{
			junc1 = tmp1;
			junc2 = tmp2;
			cost = tmp3;
		}
	}
	
	public static ArrayList<HashSet<Integer>> connsets;
	public static ArrayList<Integer> setpos;
	public static HashSet<Integer> setnums;
	
	public static PriorityQueue<juncedge> edgeheap; 
	public static edgeComparator edgecomp;
	
	public static HashSet<juncedge> edgeset;
	
	public static int gennum = 0;
	public static int juncnum = 0;
	public static int edgenum = 0;
	public static int conncount = 0;
	
	static class edgeComparator implements Comparator<juncedge> {
		public int compare(juncedge edge1, juncedge edge2) {
			 int cost1 = edge1.cost;
			 int cost2 = edge2.cost;
			 
			 if(cost1 > cost2)
				 return 1;
			 else if(cost1 < cost2)
				 return -1;
			 else
				 return 0;
		}
	}
	
	static class edgeRevComparator implements Comparator<juncedge> {
		public int compare(juncedge edge1, juncedge edge2) {
			 int cost1 = edge1.cost;
			 int cost2 = edge2.cost;
			 
			 if(cost1 > cost2)
				 return -1;
			 else if(cost1 < cost2)
				 return 1;
			 else
				 return 0;
		}
	}
	
	static void init()
	{
		setpos = new ArrayList<Integer>();
		connsets = new ArrayList<HashSet<Integer>>();
		setnums = new HashSet<Integer>();
		
		edgecomp = new edgeComparator();
		edgeheap = new PriorityQueue<juncedge>(1, edgecomp);
	
		setpos.add(-10000);
		gennum = 0;
		juncnum = 0;
		edgenum = 0;
		conncount = 0;
		
		edgeset = new HashSet<juncedge>();
	}
	
	static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
		
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			line = input.readLine();
			casenum = Integer.parseInt(line);
			for(int tmpcase=0; tmpcase < casenum; tmpcase++)
			{
				init();
				line = input.readLine();
				String [] strsplit;
				strsplit = line.split(" ");		
				juncnum = Integer.parseInt(strsplit[0]);
				edgenum = Integer.parseInt(strsplit[1]);
				gennum = Integer.parseInt(strsplit[2]);
				for(int i=0; i< juncnum; i++)
					setpos.add(-1);		
				for(int i=0; i< edgenum; i++)
				{
					line = input.readLine();
					strsplit = line.split(" ");
					int tmpjunc1 = Integer.parseInt(strsplit[0]);
					int tmpjunc2 = Integer.parseInt(strsplit[1]);
					int tmpcost = Integer.parseInt(strsplit[2]);
					juncedge tmpedge = new juncedge(tmpjunc1, tmpjunc2, tmpcost);
					edgeheap.offer(tmpedge);
				}
				int tmpcost1 = minForestGen();
				if( tmpcost1 < 0)
					System.out.printf("Impossible!\n");
				else
					System.out.println(tmpcost1);
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
	
	static int minForestGen()
	{
		if(gennum <= 0)
		{
			if(juncnum > 0)
				return -1;
			else
				return 0;
		}
		else if(gennum >= juncnum)
			return 0;
		if(edgeheap.isEmpty())
		{
			if(gennum >= juncnum)
				return 0;
			else
				return -1;
		}
		while(!edgeheap.isEmpty() && conncount < juncnum &&  juncnum-edgeset.size() > gennum)
		{
			juncedge tmpedge = edgeheap.poll();
			int junc1 = tmpedge.junc1;
			int junc2 = tmpedge.junc2;
			if(setpos.get(junc2) < 0)
			{
				//see if i-1 is in a set, if not, init a new set
				if(setpos.get(junc1) < 0)
				{
					HashSet<Integer> newset = new HashSet<Integer>();
					newset.add(junc2);
					newset.add(junc1);
					connsets.add(newset);
					int setnum = connsets.size()-1;
					setpos.set(junc2, setnum);
					setpos.set(junc1, setnum);
					setnums.add(connsets.size()-1);
					conncount+=2;
				}
				else
				{
					HashSet<Integer> tmpset = connsets.get(setpos.get(junc1));
					tmpset.add(junc2);
					setpos.set(junc2, setpos.get(junc1));
					conncount++;
					
				}
				edgeset.add(tmpedge);
			}
			else
			{
				if(setpos.get(junc1) < 0)
				{
					HashSet<Integer> tmpset = connsets.get(setpos.get(junc2));
					tmpset.add(junc1);
					setpos.set(junc1, setpos.get(junc2));
					conncount++;
					edgeset.add(tmpedge);
				}
				else if(setpos.get(junc1) != setpos.get(junc2))//combine two sets
				{
					HashSet<Integer> tmpset1 = connsets.get(setpos.get(junc2));
					HashSet<Integer> tmpset2 = connsets.get(setpos.get(junc1));
					tmpset1.addAll(tmpset2);
					setnums.remove(setpos.get(junc1));
					//don't delete tmpset2, just change setpos
					for(Integer tmppos: tmpset2)
						setpos.set(tmppos, setpos.get(junc2));
					tmpset2.clear();
					edgeset.add(tmpedge);
				}
			}		
		}
		//check if there are enough generators, or surplus generators can reduce the cost
		int allcosts = 0;
		if(juncnum - edgeset.size() > gennum)
			allcosts = -1;
		else 
		{
			for(juncedge tmpedge: edgeset)
				allcosts += tmpedge.cost;
		}
		return allcosts;
	}
	
	public static void main(String[] args)
	{
	    try {
			readFile("Power-Outage-I_testcases\\input01.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
