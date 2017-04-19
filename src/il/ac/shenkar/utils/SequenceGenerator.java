/* 
* @author Sagi Maday
*/
package il.ac.shenkar.utils;

public class SequenceGenerator {
	
	private static SequenceGenerator instance = null;
	private int serial = 0;
	
	private SequenceGenerator()
	{

	}
	
	public static SequenceGenerator getInstance() {
        if (instance == null) {
            instance = new SequenceGenerator();
        }

        return instance;
    }
	
	public String getUniqueSequence(String myIP)
	{
		return myIP + "-" + Integer.toString(getAndIncrementSerial());
	}
	
	private int getAndIncrementSerial(){
		return this.serial++;
	}

}
