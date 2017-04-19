package il.ac.shenkar.system.scheduler;

import java.util.Timer;
import java.util.TimerTask;

import il.ac.shenkar.Monitor.Monitor;

public class ScheduleMonitorRequest {

	private Timer timer = null;
	/*private Object id = null;*/
	private ScheduleInterface schedulerInterface;
	
	public ScheduleMonitorRequest(/*Object id,*/ int ms, ScheduleInterface schedulerInterface) {

        timer = new Timer();
        /*this.id = id;*/
        this.schedulerInterface = schedulerInterface;
        timer.schedule(new RemindTask(this), ms);
    }
	
	public ScheduleMonitorRequest(/*Object id,*/ int delay, int period, ScheduleInterface schedulerInterface) {

        timer = new Timer();
        /*this.id = id;*/
        this.schedulerInterface = schedulerInterface;
        timer.scheduleAtFixedRate(new RemindTask(this), delay, period);
    }
	
	/*public Object getId() {
        return id;
    }*/
    
    public ScheduleInterface getInterface() {
        return schedulerInterface;
    }
    
    public void cancel() {
        timer.cancel();
    }
    
    private class RemindTask extends TimerTask {
        private ScheduleMonitorRequest scheduler = null;

        public RemindTask(ScheduleMonitorRequest scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public void run() {
            Monitor.getInstance().StartMonitorRequest();
        }
    }
}
