/* 
* @author Sagi Maday
*/
package il.ac.shenkar.Monitor;

public class MonitorResponseMessage {

	private String path = null;
	private int pathLength = 0;
	private int numOfErrors = 0;
	private int totalMessagesSent = 0;
	private int totalDataMessagesSent = 0;
	private int totalControlMessagesSent = 0;
	private float pathAveragelenght = 0;	//do we mean to average message path length?
	private float routeTableAverageSize = 0;
	
	public MonitorResponseMessage(String path, int pathLength, int numOfErrors
	,int totalMessagesSent ,int totalDataMessagesSent ,int totalControlMessagesSent
	,float pathAveragelenght ,float routeTableAverageSize )
	{
		this.path = path;
		this.pathLength = pathLength;
		this.numOfErrors = numOfErrors;
		this.totalMessagesSent = totalMessagesSent;
		this.totalDataMessagesSent = totalDataMessagesSent;
		this.totalControlMessagesSent = totalControlMessagesSent;
		this.pathAveragelenght = pathAveragelenght;
		this.routeTableAverageSize = routeTableAverageSize;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public int getPathLength()
	{
		return pathLength;
	}
	
	public int getNumOfErrors()
	{
		return numOfErrors;
	}
	
	public int getTotalMessagesSent()
	{
		return totalMessagesSent;
	}
	
	public int getTotalDataMessagesSent()
	{
		return totalDataMessagesSent;
	}
	
	public int getTotalControlMessageAveragesSent()
	{
		return totalControlMessagesSent;
	}
	
	public float getPathAveragelenght()
	{
		return pathAveragelenght;
	}
	
	public float getRouteTableAverageSize()
	{
		return routeTableAverageSize;
	}
}
