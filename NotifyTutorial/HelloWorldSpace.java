import net.jini.space.*; 
import net.jini.core.lease.*;

public class HelloWorldSpace {
    public static void main(String args[]) {

	JavaSpace space = SpaceUtils.getSpace();
	if (space == null){
	    System.err.println("Failed to find the javaspace");
	    System.exit(1);
	}

	// create an Sobj and put it into space
	try{
	    Sobj msg = new Sobj();
	    msg.contents = "Hello World";
	    space.write(msg, null, Lease.FOREVER);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// read an Sobj from space
	Sobj template = new Sobj();
	try {
	    Sobj result = (Sobj)space.take(template, null, Long.MAX_VALUE);
	    System.out.println(result.contents);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	// all done - now quit
	System.exit(0);
    }
}
