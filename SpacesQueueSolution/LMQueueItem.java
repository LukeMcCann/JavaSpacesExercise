import net.jini.core.entry.*;

public class LMQueueItem implements Entry{
    // Variables
    public Integer jobNumber;
    public String filename;
    public String destinationPrinter;
    
    // No arg contructor
    public LMQueueItem(){
    }
    
    // Arg constructor
    public LMQueueItem(int job, String fn){
	jobNumber = new Integer(job);
	filename = fn;
    }

    // Full Arg Constructor
    public LMQueueItem(int job, String fn, String printer){
	jobNumber = new Integer(job);
	filename = fn;
	destinationPrinter = printer;
    }
}
