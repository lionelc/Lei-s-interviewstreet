import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class stringreduction {
	
	public static int num;
	public static ArrayList<String> allstrs;
	public static HashMap<String, String>  strrepo;
	
	public static String min_str(String prefix, String a, String b)
	{
		if(prefix != null)
		{
			if(a.startsWith(prefix) && !b.startsWith(prefix))
				return b;
			else if(!a.startsWith(prefix) && b.startsWith(prefix))
				return a;
		}
		if(a.length() <= b.length())
			return a;
		else 
			return b;
	}
	
	public static char replace(char a, char b)
	{
		if(a == b)
		{
			System.out.printf("error: same char! \n");
			return ' ';
		}
		HashSet<Character> tmpset = new HashSet<Character>();
		tmpset.add('a');
		tmpset.add('b');
		tmpset.add('c');
		if(tmpset.contains(a))
		{
			tmpset.remove(a);
		}
		else
		{
			System.out.printf("error: wrong char! \n");
		}
		
		if(tmpset.contains(b))
		{
			tmpset.remove(b);
		}
		else
		{
			System.out.printf("error: wrong char! \n");
		}
		Iterator<Character> tmpiter = tmpset.iterator();
		while(tmpiter.hasNext())
		{
			return tmpiter.next();
		}
		return ' ';
	}
	
	public static void readFile(String filename) throws IOException
	{
		File file = new File(filename);
		allstrs = new ArrayList<String>();
		strrepo = new HashMap<String, String>();
		
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			int count = 0;
			
			while((line = input.readLine()) != null)
			{
				if(count == 0)
				{
					num = Integer.parseInt(line);
				}
				else if(count <= num)
				{
						allstrs.add(line);
				}
				count++;
			}
		}
		finally {
			input.close();
		}
	}
	
	public static String shortest_reduction(String input, String prefix)
	{
		 int n = input.length();
		 int i=0, j=0;
		 String resultstr;
		 if(n <=1)
		 	  return input;
		 else if(n <= 2)
		 {
		    if(input.charAt(0) == input.charAt(1))
		    	  return input;
		    else
		    {
		 
		    	  String tmptarget =  String.valueOf(replace(input.charAt(0), input.charAt(1)));
		    	  if(!strrepo.containsKey(input))
		    		  strrepo.put(input, tmptarget);
		    	  return tmptarget;
		    }	
		 }
		 else
		 { 	
		 	while(input.charAt(i) == input.charAt(i+1))
		 	{
		  	 i++;
		  	 if(i >= n-1)
		  	 	  break;
		 	}
		 	if(i >= n-2)
		 	{
	    	   if(i >= n-1)
	    	   	   return input;
	    	   else
	    	   {	    	    
	    		    String targetstr0;
	    		    
	    		    if(i > 0)
	    		    	targetstr0 = input.substring(0, i);
	    		    else
	    		    	targetstr0 = new String();
		 		  	char tmpchar0 = replace(input.charAt(i), input.charAt(i+1));
		 		  	targetstr0 = targetstr0 + String.valueOf(tmpchar0);
		 		  	if(!strrepo.containsKey(targetstr0))
		 		  	{
		 		  		resultstr = shortest_reduction(targetstr0, null);
		 		  		strrepo.put(targetstr0, resultstr);
		 		  	}
		 		  	else
		 		  		resultstr = strrepo.get(targetstr0);
		 	 	  	return resultstr;
		  	  }
		  }
		  else
		 	{
		  		  String targetstr1;
		  		 
		  		  targetstr1 = input.substring(0, i);
	 		 	  char tmpchar1 = replace(input.charAt(i), input.charAt(i+1));
	 		 	  targetstr1 = targetstr1 + String.valueOf(tmpchar1);
	 		 	  targetstr1 = targetstr1 + input.substring(i+2);
	 		 	  
	 		 	  String tmpstr0;
	 		 	  if(!strrepo.containsKey(targetstr1))
	 		 	  {
	 		 		  tmpstr0 = shortest_reduction(targetstr1, null);
	 		 		  strrepo.put(targetstr1, tmpstr0);
	 		 	  }
	 		 	  else
	 		 		  tmpstr0 = strrepo.get(targetstr1);
	 		 	  
	 		 	  String targetstr2 = input.substring(0, i+1);
	 	  	
	 		 	  String tmpstr1; 
	 		 	  if(!strrepo.containsKey(targetstr2))
	 		 	  {
	 		 		  tmpstr1 = shortest_reduction(targetstr2, null);
	 		 		  strrepo.put(targetstr2, tmpstr1);
	 		 	  }
	 		 	  else
	 		 		  tmpstr1 = strrepo.get(targetstr2);
		 	  	  
		 	  	  
		 	  	  String targetstr3 = input.substring(i+1);
		 	  	  String tmpstr2; 
		 	  	  if(!strrepo.containsKey(targetstr3))
	 		 	  {
	 		 		  tmpstr2 = shortest_reduction(targetstr3, tmpstr1.substring(tmpstr1.length()));
	 		 		  strrepo.put(targetstr3, tmpstr2);
	 		 	  }
	 		 	  else
	 		 		  tmpstr2 = strrepo.get(targetstr3);
		 	  	  
		 	  	  String tmpstr3 = tmpstr1+tmpstr2;
		 	  	  String tmpstr4;
		 	  	  if(tmpstr3.compareTo(input) == 0)
		 	  	  {
		 	  	  	 tmpstr4 = tmpstr3;
		 	  	  }
		 	  	  else
		 	  	  {
		 	  	  	 if(!strrepo.containsKey(tmpstr3))
		 	  	  	 {
		 	  	  		 tmpstr4 = shortest_reduction(tmpstr3, null);
		 	  	  		 strrepo.put(tmpstr3, tmpstr4);
		 	  	  	 }
		 	  	  	 else
		 	  	  		 tmpstr4 = strrepo.get(tmpstr3);
		 	  	  }
		 	  	  resultstr = min_str(prefix, tmpstr4,  tmpstr0); 
		 	  	  //System.out.printf("str=%s auxnum = %d - %s \n", input, auxnum, resultstr);
		 	  	  return resultstr;
		 		 }

		   }
	}
	
	
	public static int findReduction(String str)
	{
		String tmpstr = shortest_reduction(str, null);
		return tmpstr.length();
	}
	
	public static void main(String[] args)
	{
	    try {
			readFile("String-Reduction_testcases\\input02.txt");
			for(String tmpstr: allstrs)
				System.out.println(findReduction(tmpstr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
