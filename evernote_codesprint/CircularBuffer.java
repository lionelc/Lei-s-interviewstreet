/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java_solutions;

import java.io.*;
import java.util.*;

/**
 *
 * @author leijiang
 */
public class CircularBuffer {
    
    public static void main(String args[]) throws Exception
    {
             FileInputStream fstream = new FileInputStream("../circular_input/input01.txt");
             DataInputStream in = new DataInputStream(fstream);
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
	     String line = br.readLine();
	     int size = Integer.parseInt(line);
             LinkedList<String> buffer = new LinkedList<String>();
             
             while((line=br.readLine())!= null && line.length()>0)
             {
                 if(line.toString().equals("Q"))
                      return;
                 else if(line.toString().equals("L"))
                 {
                     for(int i=0; i< buffer.size();i++)
                     {
                         System.out.println(buffer.get(i));
                     }
                 }
                 else 
                 {
                     String [] tmpcommand = line.split("[ ]");
                     int opnum = Integer.parseInt(tmpcommand[1]);
                     if(tmpcommand[0].equals("R"))
                     {
                         for(int i =0; i< opnum; i++)
                         {
                             buffer.remove();
                         }
                     }
                     else 
                     {
                         while(opnum > 0)
                         {
                             line = br.readLine();
                             if(buffer.size() >= size)
                                buffer.remove();
                             buffer.add(line.toString());                          
                             opnum--;
                         }
                     }
                 }
             }
    }
}
