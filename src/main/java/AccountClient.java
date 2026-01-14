/**
 *   "Java Enterprise in a Nutshell, Third Edition, 
 *    by Jim Farley and William Crawford 
 *    with Prakash Malani, John G. Norman, and Justin Gehtland. 
 *    Copyright 2006 O'Reilly Media, Inc., 0-596-10142-2."
 *    Edited by
 */

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * AccountClient: An example client for an RMI-exported Account.
 */

public class AccountClient {
  public static void main(String args[]) {
    String host;
    if (args.length < 1)
        host = null;
    else
        host = args[0];
    try {
      // Lookup account object
      Registry registry = LocateRegistry.getRegistry(host);
      Account stub = (Account) registry.lookup("BlobbyAccount");
      
      // Make deposit
      stub.deposit(12000);
      
      // Report results and balance.
      System.out.println("Deposited 12,000 into account owned by " +
                         stub.getName());
      System.out.println("Balance now totals: " + stub.getBalance());
    }
    catch (RemoteException re) {
      System.err.println("Remote exception while looking up account: "
                         + re.getMessage());
    }
    catch (NotBoundException nbe) {
      System.err.println("Server object not found at expected URL: "
                         + nbe.getMessage());
    }
  }
}
