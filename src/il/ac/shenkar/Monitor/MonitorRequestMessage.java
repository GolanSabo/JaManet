/* 
* @author Sagi Maday
*/
package il.ac.shenkar.Monitor;

public class MonitorRequestMessage {
	private int pathLength = 0;
	private String path = "";
	private String sequence = "";
	private int hopsCounter = 0;
	
	public MonitorRequestMessage(String path, int pathLength, String sequence, int hopsCounter)
	{
		this.path = path;
		this.pathLength = pathLength;
		this.sequence = sequence;
		this.hopsCounter = hopsCounter;
	}
	
	public int getHopsCounter()
	{
		return this.pathLength;
	}
	
	public int getPathLength()
	{
		return this.hopsCounter;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public String getSequence()
	{
		return this.sequence;
	}
}
