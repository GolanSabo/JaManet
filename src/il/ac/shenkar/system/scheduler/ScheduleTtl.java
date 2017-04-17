package il.ac.shenkar.system.scheduler;

import java.util.Timer;
import java.util.TimerTask;

import il.ac.shenkar.system.Dispatcher;
import il.ac.shenkar.system.QueueObject;

public class ScheduleTtl implements QueueObject{
    private Timer timer = null;
    private Object id = null;
    private ScheduleInterface schedulerInterface;
    

    public ScheduleTtl(Object id, int ms, ScheduleInterface schedulerInterface) {

        timer = new Timer();
        this.id = id;
        this.schedulerInterface = schedulerInterface;
        timer.schedule(new RemindTask(this), ms);
    }
    
    public ScheduleTtl(Object id, int delay, int period, ScheduleInterface schedulerInterface) {

        timer = new Timer();
        this.id = id;
        this.schedulerInterface = schedulerInterface;
        timer.scheduleAtFixedRate(new RemindTask(this), delay, period);
    }
    
    public Object getId() {
        return id;
    }
    
    public ScheduleInterface getInterface() {
        return schedulerInterface;
    }
    
    public void cancel() {
        timer.cancel();
        Dispatcher.getInstance().delete(this);
    }
    
    @Override
    public void dispatch() {
    	schedulerInterface.timerEvent(id);
    }
    
    private class RemindTask extends TimerTask {
        private ScheduleTtl scheduler = null;

        public RemindTask(ScheduleTtl scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public void run() {
            Dispatcher.getInstance().insert(scheduler);
        }
    }
}
