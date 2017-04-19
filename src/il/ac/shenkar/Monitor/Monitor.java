/* 
* @author Sagi Maday
*/
package il.ac.shenkar.Monitor;

import il.ac.shenkar.routing.RoutingProtocolEnum;
import il.ac.shenkar.system.managers.IpManager;
import il.ac.shenkar.system.scheduler.ScheduleInterface;
import il.ac.shenkar.system.scheduler.ScheduleTtl;
import il.ac.shenkar.utils.SequenceGenerator;

public class Monitor implements ScheduleInterface{
	
	private static Monitor instance = null;
	private StatisticsStorage statistics = null;
	private SequenceGenerator sequenceGenerator = null;
	private int numOfHops = 10;/*need to choose the real number*/
	private final String myIP;
	private ScheduleTtl scheduleTtl = null; //need to refactor ScheduleTtl name
	private final int ELECTION_TIME = 10*1000;

//	private MonitorTimer MT = new MonitorTimer();
	
	private Monitor() {
		statistics = StatisticsStorage.getInstance();
		sequenceGenerator = SequenceGenerator.getInstance();
		scheduleTtl = new ScheduleTtl(null,ELECTION_TIME,this);
		myIP = IpManager.getInstance().getIp();
    }
	
	public static Monitor getInstance() {
        if (instance == null) {
            instance = new Monitor();
        }

        return instance;
    }
	
	public void setNumOfHops(int newValue)
	{
		numOfHops = newValue;
	}
	
	public int getNumOfHops()
	{
		return this.numOfHops;
	}
//	public void getElected()
//	{
//		String[] tempPath = path.split(",");
//		String[] reversePath = new String[tempPath.length];
//		for(int i = tempPath.length; i>0 ; i--)
//		{
//			reversePath[i-1] = tempPath[tempPath.length - i ];
//		}
//		
//		
//		MonitorResponseMessage responseMessage = new MonitorResponseMessage();
//		MonitorMsgParams MRM = new MonitorMsgParams();
//		
//		MonitorReq(MRM);
//	}
	
	
	/*Monitor Request functionality*/
	public MonitorRequestMessage StartMonitorRequest()
	{
		return new MonitorRequestMessage(this.myIP, 1
				,sequenceGenerator.getUniqueSequence(this.myIP),getNumOfHops());
	}
	
	public MonitorRequestMessage UpdateMonitorRequest(String path,int pathLength, String sequence, int numberOfHops)
	{
//		MT.ResetTimer();
		path = path + "," + this.myIP;
		
		return new MonitorRequestMessage(path,pathLength + 1,sequence,numberOfHops - 1);
	}
	
	/*Monitor Request functionality*/
	/*need the sequence number also?*/
	public MonitorResponseMessage StartMonitorResponse(String path,int pathLength)
	{
//		MT.ResetTimer();
		/*creating the reverse path*/
		String[] PathArray = path.split(",");
		String reversePath = "";
		for(int i = PathArray.length; i>0 ; i--)
		{
			reversePath += PathArray[PathArray.length - i ];
		}
		reversePath+=this.myIP;
		pathLength++;
		
		return new MonitorResponseMessage(reversePath,pathLength,statistics.getErrorNumber()
				,statistics.getTotalMessageNumber(), statistics.getTotalDataMessageNumber()
				,statistics.getTotalControlMessageNumber(), statistics.getPathAveragelenght() 
				,statistics.getRouteTableAverageSize());
	}
	
	public MonitorResponseMessage UpdateMonitorResponse(String path ,int pathLength 
			,int numOfErrors ,int totalMessagesSent ,int totalDataMessagesSent 
			,int totalControlMessagesSent ,float pathAveragelenght 
			,float routeTableAverageSize)
	{
//		MT.ResetTimer();
		/*should we delete the current node from the path after finishing working on the message
		 * OR
		 * we need the information for further usage?
		 */
		
		String[] pathSplitedByNodes = path.split(",");
		String pathWithoutCurrentNode = "";
		
		for(int i = 0; i < pathSplitedByNodes.length - 1; i++)
		{
			pathWithoutCurrentNode += pathSplitedByNodes[i];
		}
		
		return new MonitorResponseMessage(pathWithoutCurrentNode , pathLength
				,statistics.SumWithErrorsNumber(numOfErrors) ,statistics.SumWithTotalMessagesNumber(totalMessagesSent)
				,statistics.SumWithTotalDataMessagesNumber(totalDataMessagesSent) 
				,statistics.SumWithTotalControlMessagesNumber(totalDataMessagesSent) 
				,statistics.CalculateWithPathAveragelenght(pathAveragelenght, totalMessagesSent)
				,statistics.CalculateWithRouteTableAverageSize(routeTableAverageSize,pathLength));
	}
	
	/*Get a decision by the statistics accumulated*/
	public RoutingProtocolEnum DecideProtocolByStatistics ()
	{
		/*decide by statistics*/
		
		return RoutingProtocolEnum.AODV;
	}

	public MonitorRequestMessage Elected(){
		
		return new MonitorRequestMessage(myIP,1
				, sequenceGenerator.getInstance().getUniqueSequence(myIP)
				, getNumOfHops());
	}
	
	/*what to do with the new message?*/
	@Override
	public void timerEvent(Object id) {
		// TODO Auto-generated method stub
		MonitorRequestMessage MonitorRequestMessage = Elected();
	}
	
	/*To-Do: change to schedule*/
//	public class MonitorTimer implements Runnable {
//		private int timer = 0;
//		private int maxTimerValue = 120;
//		
//		public MonitorTimer()
//		{
//			
//		}
//		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			while(getTimer() < getTimerGoal())
//			{
//				IncTimer();
//				try
//				{
//					Thread.sleep(1000);
//				}
//				catch(InterruptedException ie)
//				{
//					
//				}
//			}
////			Monitor.getInstance().getElected();
//		}
//		
//		private int getTimer()
//		{
//			return timer;
//		}
//		
//		private void IncTimer()
//		{
//			timer++;
//		}
//		
//		private void ResetTimer()
//		{
//			timer = 0;
//		}
//		
//		private void setTimerGoal(int g)
//		{
//			maxTimerValue = g;
//		}
//		
//		private int getTimerGoal()
//		{
//			return maxTimerValue;
//		}
//	}
}
