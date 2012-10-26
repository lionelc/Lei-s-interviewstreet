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
public class MultiplyExcept {
    public static void main(String args[]) throws Exception {
          FileInputStream fstream = new FileInputStream("../input_multiply/input01.txt");
          DataInputStream in = new DataInputStream(fstream);
	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  String line = br.readLine();
          
          int n = Integer.parseInt(line);
          long allproduct = 1;
          
          long [] input = new long[n];
         
          int zerocount = 0;
          int zeromark = 0;
          
          for(int i=0; i< n; i++)
          {
                line = br.readLine();
                long tmpvalue = Long.parseLong(line);
                if(zerocount <= 0)
                    input[i] = tmpvalue;
                if(tmpvalue == 0)
                {
                    if(zerocount >= 1)
                    {
                         for(int j=0; j<n; j++)
                         {
                           input[j] = 0;
                         }
                         zerocount++;
                         break;
                    }
                    else
                    {
                        for(int j=0; j<n; j++)
                        {
                           input[j] = 0;
                        }
                        zeromark = i;
                        zerocount++;
                    }
                }
                else
                    allproduct*=tmpvalue;
          }
          
          if(zerocount == 1)
              input[zeromark] = allproduct;
          else if(zerocount == 0)
          {
              for(int i=0; i< n; i++)
                  input[i] = allproduct/input[i];
          }
          
          for(int i=0; i<n; i++)
          {
              System.out.println(input[i]);
          }
    }
}
