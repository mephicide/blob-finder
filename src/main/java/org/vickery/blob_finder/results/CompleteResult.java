package org.vickery.blob_finder.results;

public class CompleteResult 
{
	private LeftResult left;
	private RightResult right;
	private TopResult top;
	private BottomResult bottom;
	private int numReads;
	
	public CompleteResult(LeftResult left, RightResult right, TopResult top, BottomResult bottom)
	{
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.numReads = left.getNumReads() + right.getNumReads() + top.getNumReads() + bottom.getNumReads();
	}
	
	@Override
	public String toString() 
	{
		return "Cell Reads: " + numReads + "\n" +
				top + left + bottom + right;
	}
}
