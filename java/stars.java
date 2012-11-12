import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class stars {
	public static class star
	{
		public int x;
		public int y;
		public int w;
		public int next;
		
		public star(int tmpx, int tmpy, int tmpw, int tmpn)
		{
			x = tmpx;
			y = tmpy;
			w = tmpw;
			next = tmpn;
		}
	}
	
	public static ArrayList<star> allstars;
	public static int pnum;
	public static int sum;
	
	static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
		allstars = new ArrayList<star>();
		sum = 0;
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			int count = 0;
			while((line = input.readLine()) != null)
			{
				if(count == 0)
				{
					 pnum = Integer.parseInt(line);
				}
				else
				{
					String [] strsplit;
					strsplit = line.split(" ");
					int tmpx = Integer.parseInt(strsplit[0]);
					int tmpy = Integer.parseInt(strsplit[1]);
					int tmpw = Integer.parseInt(strsplit[2]);
					star tmps = new star(tmpx, tmpy, tmpw, -1);
					sum += tmpw;
					allstars.add(tmps);
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
	
	
	static public int findSplit(int tmp1, int tmp2)
	{
		  int curmax1 = 0, curmax2 = 0, curmax3 = 0, curmax4 = 0;
		  int subsum = 0;
		  int curmax = 0;
	      for(int i =0; i< allstars.size(); i++)
	      {
	    	  if(i == tmp1 || i == tmp2)
	    		  continue;
	          //judge if it is above the diagonal
	          float asslat = (float)(allstars.get(tmp1).x)+(float)(allstars.get(i).y-allstars.get(tmp1).y)/(float)
	        		  (allstars.get(tmp2).y - allstars.get(tmp1).y)*(float)(allstars.get(tmp2).x-allstars.get(tmp1).x);
	          if(allstars.get(i).x >= asslat) 
	          {
	              subsum += allstars.get(i).w;
	          }
	      }
	  
	      int tmpsum = subsum;
	      if((float)tmpsum < (float)sum/2.0)
	    	  curmax1 = tmpsum;
	      else
	    	  curmax1 = sum-tmpsum;     
	      tmpsum += allstars.get(tmp1).w+allstars.get(tmp2).w;
	      if((float)tmpsum < (float)sum/2.0)
	    	  curmax2 = tmpsum;
	      else
	    	  curmax2 = sum-tmpsum;
	      curmax = Math.max(curmax1, curmax2);
	      tmpsum = subsum;
	      tmpsum += allstars.get(tmp1).w;
	      if((float)tmpsum < (float)sum/2.0)
	    	  curmax3 = tmpsum;
	      else
	    	  curmax3 = sum-tmpsum;
	      curmax = Math.max(curmax, curmax3);
	      tmpsum = subsum;
	      tmpsum += allstars.get(tmp2).w;
	      if((float)tmpsum < (float)sum/2.0)
	    	  curmax4 = tmpsum;
	      else
	    	  curmax4 = sum-tmpsum;
	      curmax = Math.max(curmax, curmax4);
	      return curmax;
	}
	
	public static void main(String[] args)
	{
		 try {
				readFile("Stars_testcases\\input00.txt");
				int max = 0;
				int earlyflag = 0;
				for(int i=0; i< pnum; i++)
				{
					for(int j=i+1; j< pnum; j++)
					{
						int tmpmax = findSplit(i,j);
						if(max < tmpmax)
							max = tmpmax;
						if(max >= sum/2)
						{
							earlyflag = 1;
							break;
						}
					}
					if(earlyflag > 0)
						break;
				}
				System.out.println(max);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
}
