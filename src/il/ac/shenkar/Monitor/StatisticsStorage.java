/* 
* @author Sagi Maday
*/
package il.ac.shenkar.Monitor;

public class StatisticsStorage {
	
	private static StatisticsStorage instance = null;

	private int numOfErrors = 0;
	private int totalMessagesSent = 0;
	private int totalDataMessagesSent = 0;
	private int totalControlMessagesSent = 0;
	private float pathAveragelenght = 0;	//do we mean to average message path length?
	private float routeTableAverageSize = 0;
//	private int numberOfRouteTablesCalculated = 0;
	
	public StatisticsStorage(){
		
	}
	
	public static StatisticsStorage getInstance() {
        if (instance == null) {
            instance = new StatisticsStorage();
        }

        return instance;
    }
	
	/*inner data collecting*/
	public void IncrementErrorNumber(){
		numOfErrors++;
	}
	
	public void IncrementTotalMessageNumber(){
		totalMessagesSent++;
	}

	public void IncrementTotalDataMessageNumber(){
		totalDataMessagesSent++;
	}
	
	public void IncrementTotalControlMessagesNumber(){
		totalControlMessagesSent++;
	}

	public void RecalculateRouteTableAverageSize(){
		/*RouteTable.CalcAverage()*/
	}
	
	/*do we mean to average message path length?
	 *split the path of nodes ips by the ',' delimiter*/
	public void RecalculatePathAveragelenght(String path){
		int newPathLength = path.split(",").length;
		pathAveragelenght = (totalMessagesSent + 1) * (pathAveragelenght + newPathLength);
	}

	
	/*calculate with external data*/
	public int SumWithErrorsNumber(int value){
		return getErrorNumber() + value;
	}
	
	public int SumWithTotalMessagesNumber(int value){
		return getTotalMessageNumber() + value;
	}
	
	public int SumWithTotalDataMessagesNumber(int value){
		return getTotalDataMessageNumber() + value;
	}
	
	public int SumWithTotalControlMessagesNumber(int value){
		return getTotalControlMessageNumber() + value;
	}

	/*do we mean to average message path length?*/
	public float CalculateWithPathAveragelenght(float otherPathLength, int otherTotalMessages ){
		return ((pathAveragelenght * totalMessagesSent) + (otherPathLength * otherTotalMessages)) / (totalMessagesSent + otherTotalMessages);
	}
	
	public float CalculateWithRouteTableAverageSize(float newTableAvrageToAdd , int pathLength){
		/*3rd option - divide by number of hops in monitor when take decision*/
		return routeTableAverageSize + newTableAvrageToAdd;
		
		/*2nd option*/
//		return ((routeTableAverageSize + (newTableAvrageToAdd * pathLength)) / (pathLength + 1));
		
		/*1st option*/
//		float TempRouteTableAverageSize = routeTableAverageSize;
//		int TempNumberOfRouteTablesCalculated = numberOfRouteTablesCalculated;
//		
//		TempRouteTableAverageSize *= TempNumberOfRouteTablesCalculated;
//		TempNumberOfRouteTablesCalculated++;
//		TempRouteTableAverageSize += newTableAvrageToAdd;
//		TempRouteTableAverageSize /= TempNumberOfRouteTablesCalculated;
//		
//		return TempRouteTableAverageSize;
		
	}

	/*simple getters*/
	public int getErrorNumber(){
		return numOfErrors;
	}
	
	public int getTotalMessageNumber(){
		return totalMessagesSent;
	}

	public int getTotalDataMessageNumber(){
		 return totalDataMessagesSent;
	}
	
	public int getTotalControlMessageNumber(){
		 return totalControlMessagesSent;
	}
	
	public float getPathAveragelenght(){
		return pathAveragelenght;
	}
	
	public float getRouteTableAverageSize(){
		return routeTableAverageSize;
	}
	
	
}
