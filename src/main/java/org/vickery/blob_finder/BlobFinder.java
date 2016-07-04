package org.vickery.blob_finder;

import org.vickery.blob_finder.results.BottomResult;
import org.vickery.blob_finder.results.CompleteResult;
import org.vickery.blob_finder.results.LeftResult;
import org.vickery.blob_finder.results.RightResult;
import org.vickery.blob_finder.results.TopResult;

/**
 * - We are given a 10x10 array of boolean values
 * - We are told that the array represents "a blob"
 * - We must find the minimum row, maximum row, minimum column,
 *   and maximum column which contains a 'true' value
 * - We must minimize the number of cell reads
 * - Assumption: Space is an array of rows, where each subarray is the associated column
 * 
 * @author vickery
 *
 */
public class BlobFinder 
{

	public CompleteResult findBlobBoundaries(boolean[][] space)
	{
		TopResult topResult = findTop(space);
		LeftResult leftResult = findLeft(space, topResult.getResult(), topResult.getOtherCoord());
		RightResult rightResult = findRight(space, topResult.getResult(), topResult.getOtherCoord());
		BottomResult bottomResult = findBottom(space, 
												leftResult.getResult(), 
												leftResult.getOtherCoord(),
												rightResult.getResult(),
												rightResult.getOtherCoord());
		
		return new CompleteResult(leftResult, rightResult, topResult, bottomResult);
	}
	
	/**
	 * Scans left-to-right and top-to-bottom.  We pick up at the row past where we found our top boundary
	 * since it also scans left-to-right, so we won't find anything here we didn't find there.
	 *  
	 * @param space
	 * @param rowTopFound
	 * @param colTopFound
	 * @return
	 */
	private LeftResult findLeft(boolean[][] space, int rowTopFound, int colTopFound)
	{
		LeftResult result = new LeftResult();
		//if we found the top at the first x-position, simply return it
		if(colTopFound==0)
		{
			result.setResult(colTopFound);
			result.setOtherCoord(rowTopFound);
			return result;
		}
		
		//slower loop iterates from left-to-right
		for(int col=0; col<colTopFound; col++)
		{
			//faster loop iterates over top-to-bottom
			for(int row=rowTopFound+1; row< space.length; row++)
			{
				result.addRead();
				if(space[row][col])
				{
					result.setResult(col);
					result.setOtherCoord(row);
					return result;
				}
			}
		}
		
		result.setResult(colTopFound);
		result.setOtherCoord(rowTopFound);
		return result;
	}
	
	/**
	 * Scans from right-to-left, and top-to-bottom. We initiate our search at the row the top value
	 * was discovered (inclusively) because it scans the opposite way and may have missed a more-right
	 * value.  We scan left until, but before, the row at which the top value was found, because by the time
	 * we get there, we haven't found anything more right than that value.
	 * 
	 * @param space
	 * @param rowTopFound
	 * @param colTopFound
	 * @return
	 */
	private RightResult findRight(boolean[][] space, int rowTopFound, int colTopFound)
	{
		RightResult result = new RightResult();
		if(colTopFound==space[0].length-1)
		{
			result.setResult(colTopFound);
			result.setOtherCoord(rowTopFound);
			return result;
		}
		
		//slow loop iterates over columns
		for(int col=space[0].length-1; col>colTopFound; col--)
		{
			//fast loop iterates over rows.  Start on row top was found, since
			//we scaned for top from left-to-right, and didn't consume right-most cells
			for(int row=rowTopFound; row< space.length; row++)
			{
				result.addRead();
				if(space[row][col])
				{
					result.setResult(col);
					result.setOtherCoord(row);
					return result;
				}
			}
		}
		
		result.setResult(colTopFound);
		result.setOtherCoord(rowTopFound);
		return result;
	}
	
	/**
	 * Scans top-to-bottom and left-to-right, bailing as soon as it finds a 'true' value,
	 * yielding the coordinates at which the result was found.
	 * 
	 * @param space
	 * @return
	 */
	private TopResult findTop(boolean[][] space)
	{
		TopResult result = new TopResult();
		//slow loop iterates over rows
		for(int row=0; row< space.length; row++)
		{
			//fast loop iterates over columns
			for(int col=0; col<space[row].length; col++)
			{
				result.addRead();
				if(space[row][col])
				{
					result.setResult(row);
					result.setOtherCoord(col);
					return result;
				}
				
			}
		}
		
		throw new RuntimeException("Improperly specified blob space has no 'true' cell values.");
	}
	
	/**
	 * Scans from bottom-to-top, from the leftmost column to the rightmost column, inclusive on both ends,
	 * since neither of those scans went lower than the discovered value (we might find an even lower boundary
	 * in those columns).  We only go up until, but before, the lowest of either side boundaries, because
	 * if we don't find anything up until that point, we don't need to scan to see if there are any more 
	 * acceptable values in the bottom-most discovered row.
	 *  
	 * @param space
	 * @param colLeftFound
	 * @param rowLeftFound
	 * @param colRightFound
	 * @param rowRightFound
	 * @return
	 */
	private BottomResult findBottom(boolean[][] space, 
									int colLeftFound, 
									int rowLeftFound, 
									int colRightFound, 
									int rowRightFound)
	{
		int maxRow = Math.max(rowRightFound, rowLeftFound);
		
		BottomResult result = new BottomResult();
		if(maxRow==space.length-1)
		{
			result.setResult(space.length-1);
			//don't worry about other coord, no one needs it now
			return result;
		}
		
		for(int row=space.length-1; row> maxRow; row--)
		{
			for(int col=colLeftFound; col<=colRightFound; col++)
			{
				result.addRead();
				if(space[row][col])
				{
					result.setResult(row);
					return result;
				}
				
			}
		}
		
		result.setResult(maxRow);
		return result;
	}
}
