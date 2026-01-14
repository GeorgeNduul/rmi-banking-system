/**
 *   "Java Enterprise in a Nutshell, Third Edition, 
 *    by Jim Farley and William Crawford 
 *    with Prakash Malani, John G. Norman, and Justin Gehtland. 
 *    Copyright 2006 O'Reilly Media, Inc., 0-596-10142-2."
 *    Edited by
 */

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * AccountServer: A utility class that registers an Account with the local
 * RMI registry for remote access
 */

public class AccountServer {
    public static void main(String argv[]) {
        try {
            // Make an Account with a given name
            AccountImpl acct = new AccountImpl("Mr_Blobby");
 	    Account stub = (Account) UnicastRemoteObject.exportObject(acct, 0);
            // Register it with the local naming registry
            Registry registry = LocateRegistry.getRegistry();
	    registry.rebind("BlobbyAccount", stub);

            System.out.println("\nRegistered account as BlobbyAccount");
            
            // Keep the server process alive indefinitely
            Object dummy = new Object();
            synchronized (dummy) {
                try {
                    dummy.wait();
                }
                catch (InterruptedException ie) {}
            }
        }
        catch (RemoteException re) {
            System.err.println("Remote exception while creating/registering: "
                               + re.getMessage());
        }
    }
}
