import net.jini.space.*;
import net.jini.core.lease.*;
import java.awt.*;
import javax.swing.*;


public class PrintJobPrinter extends JFrame{
    private JavaSpace space;
    private JPanel jPanel1;
    private JTextArea jobList;
    
    public PrintJobPrinter() {
	space = SpaceUtils.getSpace();
	if (space == null){
	    System.err.println("Failed to find the javaspace");
	    System.exit(1);
	}
	
	initComponents ();
	pack ();
	setVisible(true);
	processPrintJobs();
    }
    
    private void initComponents () {
	setTitle ("Print Job Printer");
	addWindowListener (new java.awt.event.WindowAdapter () {
	    public void windowClosing (java.awt.event.WindowEvent evt) {
		System.exit(0);
	    }
	}   );
	
	Container cp = getContentPane();
	cp.setLayout (new BorderLayout ());

	jPanel1 = new JPanel();
	jPanel1.setLayout(new FlowLayout());

	jobList = new JTextArea(30,30);
	jPanel1.add(jobList);

	cp.add(jPanel1,"Center");
    }

    public void processPrintJobs(){
	while(true){
	    try {
		QueueItem qiTemplate = new QueueItem();
		QueueItem nextJob = (QueueItem)space.take(qiTemplate,null,Long.MAX_VALUE);
		int nextJobNumber = nextJob.jobNumber.intValue();
		String nextJobName = nextJob.filename;
		jobList.append("Job Number: " + nextJobNumber + " Filename: " + nextJobName + "\n" );
	    }  catch ( Exception e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(java.lang.String[] args) {
	new PrintJobPrinter();
    }
}
