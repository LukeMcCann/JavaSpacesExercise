import net.jini.core.entry.*;

public class QueueStatus implements Entry{
    // Variables
    public Integer nextJob;
    
    // No arg contructor
    public QueueStatus (){
    }

    public QueueStatus (int n){
	// set count to n
	nextJob = new Integer(n);
    }

    public void addJob(){
	nextJob = new Integer(nextJob.intValue() + 1);
    }
}
