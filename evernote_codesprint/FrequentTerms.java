/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java_solutions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

/**
 *
 * @author leijiang
 */
public class FrequentTerms {
    
    
    public static class Pair
    {
        public String fst;
        public Integer snd;
        
        Pair(String key, Integer value)
        {
            fst = key;
            snd = value;
        }
    }
    
    public static class FrequencyComp implements Comparator<Pair>
    {
        //only used for back-up
        public int compare(Pair o1, Pair o2)
        {
            if(o1.snd > o2.snd)
                return 1;
            else if(o1.snd < o2.snd)
                return -1;
            else if(!o1.fst.equals(o2.fst))
            {
                return -1*o1.fst.compareTo(o2.fst);
            }
            else
                return 0;
        }
        
    }
     
     public static void main(String args[]) throws Exception
    {  
         
         FileInputStream fstream = new FileInputStream("../input_freq/input01.txt");
                DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = br.readLine();
		int n = Integer.parseInt(line);
                HashMap<String, Integer> wordmap = new HashMap<String, Integer>();
                FrequencyComp freqcomp = new FrequencyComp();
                for(int i=0; i<n; i++)
                {
                    line = br.readLine();
                    if(wordmap.containsKey(line))
                        wordmap.put(line, wordmap.get(line)+1);
                    else
                        wordmap.put(line,1);
                }
                
                int resultnum = Integer.parseInt(br.readLine());
                //sort the hash based on frequent
                PriorityQueue<Pair> pque = new PriorityQueue<Pair>(resultnum, freqcomp);
                
                for(String tmpkey : wordmap.keySet())
                {
                    Pair tmppair = new Pair(tmpkey, wordmap.get(tmpkey));
                    if(pque.size() < resultnum)
                    {
                        pque.offer(tmppair);
                    }
                    else
                    {
                        if(freqcomp.compare(tmppair, pque.peek()) > 0)
                        {
                            pque.poll();
                            pque.offer(tmppair);
                        }
                    }
                
                }
                //put pque into an array for better sorting
                Pair[] result;
                result = (Pair[])java.lang.reflect.Array.newInstance(Pair.class,resultnum);
                for(int i =0; i< resultnum; i++)
                {
                    result[i] = pque.remove();
                }
                Arrays.sort(result, freqcomp);
                for(int i=resultnum-1; i>=0; i--)
                {
                    System.out.println(result[i].fst);
                }
     }
             
}
