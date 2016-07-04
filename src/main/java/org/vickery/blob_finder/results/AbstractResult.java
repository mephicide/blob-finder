package org.vickery.blob_finder.results;

public abstract class AbstractResult 
{
	protected abstract String resultString();
	protected int result = 0;
	protected int numReads = 0;
	protected int otherCoord = 0;
	
	public int getNumReads()
	{
		return numReads;
	}
	
	public void addRead()
	{
		numReads++;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	public void setOtherCoord(int coord)
	{
		this.otherCoord = coord;
	}
	
	public int getOtherCoord()
	{
		return this.otherCoord;
	}
	
	@Override
	public String toString() 
	{
		return resultString() + ": " + getResult() + "\n";
	}
}
