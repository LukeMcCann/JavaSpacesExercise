import net.jini.core.entry.*;

public class LMPrinterQueueItem implements Entry{
    // Variables
    public Integer jobNumber;
    public String filename;
    public String printerName;   // Extra field added to contain destination printer name

    // No arg contructor
    public LMPrinterQueueItem(){
    }

    // Arg constructor
    public LMPrinterQueueItem(int job, String fn, String pn){  // Constructor edited to take extra parameter
        jobNumber = job;
        filename = fn;
        printerName = pn;  // New line to set the destination printer name
    }
}
