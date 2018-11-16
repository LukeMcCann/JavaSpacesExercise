import java.rmi.server.*;
import java.rmi.RemoteException;
import net.jini.core.event.*;
import net.jini.core.lease.Lease;
import net.jini.space.JavaSpace;

import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;


public class HelloWorldNotify implements RemoteEventListener {

    private JavaSpace space;
    private RemoteEventListener theStub;

    public HelloWorldNotify() {
	// find the space
	space = SpaceUtils.getSpace();
	if (space == null){
	    System.err.println("Failed to find the javaspace");
	    System.exit(1);
	}
	
	// create the exporter
	Exporter myDefaultExporter = 
	    new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
				  new BasicILFactory(), false, true);

	try {
	    // register this as a remote object 
	    // and get a reference to the 'stub'
	    theStub = (RemoteEventListener) myDefaultExporter.export(this);

	    // add the listener
	    Sobj template = new Sobj();
	    space.notify(template, null, this.theStub, Lease.FOREVER, null);

	} catch (Exception e) {
	    e.printStackTrace();
	}

	// create an example object being listened for
	try{
	    Sobj msg = new Sobj();
	    msg.contents = "Hello World";
	    space.write(msg, null, Lease.FOREVER);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void notify(RemoteEvent ev) {
	// this is the method called when we are notified
	// of an object of interest
	Sobj template = new Sobj();
	
	try {
	    Sobj result = (Sobj)space.take(template, null, Long.MAX_VALUE);
	    System.out.println(result.contents);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// that's all we need to do in this demo so we can quit...
	System.exit(0);
    }
    
    public static void main(String[] args) throws RemoteException{
	// set up the security manager
	if (System.getSecurityManager() == null)
	    System.setSecurityManager(new SecurityManager());

	// run the object
	new HelloWorldNotify();
    }
}
