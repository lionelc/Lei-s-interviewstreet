/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java_solutions;

import java.io.*;
import java.util.*;

public class TopFour {
        public static class LessThan implements Comparator<Integer>
        {
            //only used for back-up
            public int compare(Integer o1, Integer o2)
            {
                if(o1 > o2)
                   return -1;
                else if(o1 < o2)
                   return 1;
                else
                    return 0;
            }
        }
    
        
	public static void main(String args[]) throws Exception {
                FileInputStream fstream = new FileInputStream("../input_top4/input01.txt");
                DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = br.readLine();
		int n = Integer.parseInt(line);
		PriorityQueue<Integer> pque = new PriorityQueue(4);
                while((line = br.readLine()) != null && line.length() > 0)
                {
                     int tmpv = Integer.parseInt(line);
                     if(pque.size() < 4)
                         pque.offer(tmpv);
                     else if(tmpv > pque.peek())
                     {
                        pque.poll();
                        pque.offer(tmpv);
                     }
                }
                
                System.out.printf("%d\n", pque.size());
                
                int [] result = new int [4];
                int i =0;
                while(!pque.isEmpty())
                {
                    result[i] = pque.remove();
                    i++;
                }
                Arrays.sort(result);
                
                for(i=3; i>= 0; i--)
                {
                    System.out.printf("%d\n",result[i]);
                }
	}
}
