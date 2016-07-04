package org.vickery.blob_finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.vickery.blob_finder.results.CompleteResult;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	InputStream is = null;
    	if(args.length==0)
    	{
    		is = App.class.getResourceAsStream("test-blob-1.txt");
    	}
    	else if(args.length==1)
        {
        	if(args[0].contains("help"))
        		printUsage();
        	else
        	{
        		is = new FileInputStream(new File(args[0]));
        	}
        }
        else
        {
        	printUsage();
        }
        
        if(is!=null)
        {
        	boolean[][] space = adaptFileContentsToBooleanArray(is);
    		CompleteResult result = new BlobFinder().findBlobBoundaries(space);
    		System.out.println(result);
        }
    }

	private static boolean[][] adaptFileContentsToBooleanArray(InputStream is) throws IOException
	{
		BufferedReader br = null;
		ArrayList<ArrayList<Boolean>> spaceTemp = new ArrayList<ArrayList<Boolean>>();
		
		boolean[][] space = null;
		int rowCounter = 0;
		
		try
		{
			br = new BufferedReader(
					new InputStreamReader(
							is, Charset.forName("UTF-8")));
			String oneLine = "";
			while((oneLine = br.readLine())!=null)
			{
				ArrayList<Boolean> thisRow = new ArrayList<Boolean>();
				
				String[] rowContents = oneLine.split("\\s+");
				rowCounter++;
				for(int i=0; i<rowContents.length; i++)
				{
					int val = Integer.parseInt(rowContents[i]);
					if(val==0)
						thisRow.add(false);
					else if(val==1)
						thisRow.add(true);
					else
						throw new RuntimeException("Improperly specified boolean value: "
									+ "must be '0' or '1' at position (" + rowCounter + ", " + i + ")");
				}
				
				spaceTemp.add(thisRow);
			}
		}finally
		{
			if(br!=null)
				br.close();
		}
		
		ArrayList<Boolean> firstRow = spaceTemp.get(0);
		space = new boolean[spaceTemp.size()][firstRow.size()];
		
		for(int i=0; i<spaceTemp.size(); i++)
		{
			ArrayList<Boolean> thisRow = spaceTemp.get(i);
			for(int j=0; j<thisRow.size(); j++)
			{
				space[i][j] = thisRow.get(j);
			}
		}
		return space;
	}

	private static void printUsage() 
	{
		System.out.println("Find the blob boundaries within the given 10x10 space.  A space is defined as"
				+ " a double array of boolean values (indicated by 1's and 0's for the sample input).  "
				+ " Acceptable input is a UTF-8-encoded text file with 1's and 0's separated by whitespace, the"
				+ "rows of which are delimited by newlines.");
	}
}
