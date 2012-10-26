import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class eventree {
	
	public static class treenode
	{
		int num;
		int level;
		int childnum;
		treenode parent;
	}
	
	public static ArrayList<HashSet<Integer>> treenodes;
	//public static HashSet<Integer> outnodes;
	public static int vertnum;
	public static int edgenum;
	public static HashSet<Integer> travset;
	//public static Queue<treenode> leafqueue;
	
	public static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
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
					vertnum = Integer.parseInt(strsplit[0]);
					edgenum = Integer.parseInt(strsplit[1]);
					treenodes = new ArrayList<HashSet<Integer>>();
					//System.out.println(vertnum);
					//System.out.println(edgenum);
					for(int i=0; i< vertnum; i++)
					{
						HashSet<Integer> tmphash = new HashSet<Integer>();
						treenodes.add(tmphash);
					}
					//outnodes = new HashSet<Integer>();
				}
				else
				{
					int vert1 = Integer.parseInt(strsplit[0]);
					int vert2 = Integer.parseInt(strsplit[1]);
					HashSet<Integer> tmpvert = treenodes.get(vert1-1);
					tmpvert.add(vert2-1);
					tmpvert = treenodes.get(vert2-1);
					tmpvert.add(vert1-1);
				}
				count++;
			}
		}
		finally {
			input.close();
		}
	}
	
	public static int buildTree()
	{
		//build the tree first
		int root = 0;
		travset = new HashSet<Integer>();
		//leafqueue = new ConcurrentLinkedQueue<treenode>();
		Queue<Integer> tmpqueue = new ConcurrentLinkedQueue<Integer>();
		tmpqueue.offer(root);
		ArrayList<treenode> allnodes = new ArrayList<treenode>();
		for(int i=0; i< vertnum; i++)
		{
			treenode tmpnode = new treenode();
			tmpnode.num = i;
			tmpnode.childnum = 0;
			tmpnode.level = 0;
			tmpnode.parent = null;
			allnodes.add(tmpnode);
		}
		
		allnodes.get(root).childnum = 0; //treenodes.get(root).size();
		travset.add(root);
		int curlevel = 1;
		while(!tmpqueue.isEmpty())
		{
			Iterator<Integer> tmpiter = tmpqueue.iterator();
			while(tmpiter.hasNext())
			{
				int parentnum = tmpiter.next();
				HashSet<Integer> childrenset = treenodes.get(parentnum);
				Iterator<Integer> tmpiter2 = childrenset.iterator();
				int tmpchildnum = 0;
				while(tmpiter2.hasNext())
				{
					int curnum = tmpiter2.next();	
					
					if(!travset.contains(curnum))
					{
						allnodes.get(curnum).parent = allnodes.get(parentnum);
						allnodes.get(curnum).level = curlevel;
						tmpqueue.add(curnum);
						travset.add(curnum);
						//int tmpdegree = allnodes.get(curnum).childnum;
						//if(tmpdegree == 0)
						//	leafqueue.offer(allnodes.get(curnum));
						tmpchildnum++;
					}
					
				}
				allnodes.get(parentnum).childnum = 0; //tmpchildnum;
				tmpqueue.remove(parentnum);
			}
			curlevel++;
		}
		
		//print tree
		//add all the childnum from bottom to root
		for(int i=0; i< vertnum; i++)
		{
			treenode tmpparent = allnodes.get(i);
			while( tmpparent.parent != null)
			{
				tmpparent.parent.childnum++;
				tmpparent = tmpparent.parent;
			}
		}
		
		/*Iterator<treenode> tmpiter3 = allnodes.iterator();
		while(tmpiter3.hasNext())
		{
			treenode tmpnode = tmpiter3.next();
			if(tmpnode.parent != null)
				System.out.printf("node = %d  level= %d  childnum=%d  parent=%d \n", tmpnode.num, tmpnode.level, tmpnode.childnum, tmpnode.parent.num);
			else
				System.out.printf("node = %d  level= %d  childnum=%d  parent=-1 \n", tmpnode.num, tmpnode.level, tmpnode.childnum);
		}*/
		
		int remnum = 0;
		for(int i=0; i< vertnum; i++)
		{
			if(i == root)
				continue;
			if(allnodes.get(i).childnum % 2 == 1)
				remnum++;
		}
		return remnum;
		//return 0;
	}
	
	public static void main(String[] args)
	{
	    try {
			readFile("Even-Tree_testcases\\input00.txt");
			System.out.println(buildTree());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
