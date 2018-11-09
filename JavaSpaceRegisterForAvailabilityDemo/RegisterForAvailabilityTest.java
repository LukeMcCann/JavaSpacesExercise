import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.event.UnknownEventException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.AvailabilityEvent;
import net.jini.space.JavaSpace05;


public class RegisterForAvailabilityTest {

	// This is a demo of the JavaSpace05 "registerForAvailability" functionality
	// This is an advanced version of the "notify" functionality, which can deliver
	// a copy of the object that triggered the notification.

	private static final int FIVE_SECONDS = 1000 * 5;


	public static void main(String[] args) {
		// Get a reference to the space, and cast it to a JavaSpace05
		JavaSpace05 space = (JavaSpace05) SpaceUtils.getSpace();
		if(space == null) {
			System.err.println("JavaSpace not found.");
			System.exit(1);
		}

		// registerForAvailability requires a list of templates
		// For this demo we will set up the List of templates, but add
		//     just one template to that list
		List<Lot> templates = new ArrayList<Lot>();

		Lot template = new Lot();
		template.name = "Testing";  // we're only looking for objects that match this template
		templates.add(template);

		// For purposes of the test we want to add, notify, add, notify, ...
		// To do this we will use thread locking.
		// This is NOT normally necessary, but if we don't do it here then the
		// notify will fire too slowly for the demo to work properly (due to network lag).
		// It's simply because we are writing and notifying all in one single demo.
		// Try removing the object locking (in two places below) to see what happens.
		final Object lock = new Object();

		RemoteEventListener listener = new RemoteEventListener() {
			@Override
			public void notify(RemoteEvent theEvent) throws UnknownEventException, RemoteException {
				// Cast the RemoteEvent to an AvailabilityEvent, as this adds extra functionality
				AvailabilityEvent event = (AvailabilityEvent) theEvent;

				try {
					// AvailabilityEvent provides an extra method to get
					//     the entry that fired the notification
					Lot lot = (Lot) event.getEntry();

					System.out.println("'notify' method fired and passed Lot with ID " + lot.id);

				} catch (UnusableEntryException e) {
					System.err.println(e.getMessage());
				} finally {
					// Remember - sequential execution is required to make this test work
					// Comment this out to try it without the locking
					synchronized(lock) {
						lock.notify();
					}
				}

			}
		};

		try {
			// export the listener object, so its "notify" method can be called remotely from the space
			UnicastRemoteObject.exportObject(listener, 0);

			// add the "registerForAvailabilityEvent, much like adding a "notify" to the space
			space.registerForAvailabilityEvent(templates, null, false, listener, Lease.FOREVER, null);	


			// create an object to write to the space, so as to trigger the notifications
			Lot lot = new Lot();
			lot.name = "Testing";

			// write it 10 times, with different contents each time

			System.out.println("Starting to add Lots to space... \n");
			for(int i = 0; i < 10; ++i) {
				// Add a lot with a unique ID - the current time will do
				lot.id = System.nanoTime();
				space.write(lot, null, FIVE_SECONDS);

				System.out.println("Lot with ID " + lot.id + " added to the space");

				// Remember - sequential execution is required to make this test work
				// Comment this out to try it without the locking
				synchronized(lock) {
					lock.wait();
				}
			}


			// Take all lots that match our template back out of the space
			// This is just to tidy up, really.
			int lotsTaken = 0;
			boolean somethingToTake = true;

			System.out.println("\n\nStarting to remove all the Lots from the space...\n");

			while(somethingToTake) {
				try {
					Lot lotTaken = (Lot) space.takeIfExists(template, null, 0);

					if(lotTaken != null) {
						System.out.println("Removed a Lot with ID " + lotTaken.id);
						lotsTaken++;
					} else {
						somethingToTake = false;
					}

				} catch (UnusableEntryException e) {
					e.printStackTrace();
				}
			}

			System.out.println("\nTest ended. " + lotsTaken + " lots were removed from the space\n");

			System.exit(0);

		} catch(InterruptedException | RemoteException | TransactionException e) {
			e.printStackTrace();
		}
	}
}
