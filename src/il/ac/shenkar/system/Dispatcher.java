package il.ac.shenkar.system;

import java.util.concurrent.LinkedBlockingQueue;

import il.ac.shenkar.communication.Sniffer;

public class Dispatcher {

    /**
     * Private data
     */
    private static Dispatcher           		instance    = null;
    private LinkedBlockingQueue<QueueObject> 	queue       = null;
    private Thread                      		thread      = null;
    private Sniffer								sniffer		= null;

    /**
     * Constructor
     */
    private Dispatcher() {
        queue       = new LinkedBlockingQueue<QueueObject>();
        thread      = new Thread( new Runnable() {
            @Override
            public void run() {
                loop();
            }
        } );
    }

    /**
     * Returns singleton instance
     */
    public static Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }

        return instance;
    }

    /**
     * Starts the module and the underlying modules
     */
    public void start() {
    	sniffer.start();
    	
    }

    /**
     * Runs the infinite loop of waiting on the queue and dispatching events.
     */
    public void run() {

        if (thread.isAlive() == false) {
            thread.start();
        }
    }

    /**
     * Insert an object to the queue
     */
    public void insert(QueueObject object) {
        queue.add(object);
    }
    
    public void delete(QueueObject object) {
    	queue.remove(object);
    }

    /**
     * Infinite loop of the waiting thread.
     */
    private void loop() {

        while (true) {
            try {
                dispatch(queue.take());
            } catch (InterruptedException e) {
            }
        } // while (true)
    }

    /**
     * Dispatch the received event to the registered client.
     */
    private void dispatch(QueueObject obj) {

        if (obj != null)
            obj.dispatch();
    }
}
