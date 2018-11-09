import net.jini.core.entry.*;

public class NamedQueueItem implements Entry{
    // Variables
    public Integer jobNumber;
    public String filename;
    public String destinationPrinter;
    
    // No arg contructor
    public NamedQueueItem (){
    }
    
    // Arg constructor
    public NamedQueueItem (int job, String fn){
	jobNumber = new Integer(job);
	filename = fn;
    }

    // Full Arg Constructor
    public NamedQueueItem (int job, String fn, String printer){
	jobNumber = new Integer(job);
	filename = fn;
	destinationPrinter = printer;
    }
}
