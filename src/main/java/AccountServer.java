
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class AccountServer {
    public static void main(String[] argv) {
        try {
            // (Optional but recommended) If you have multiple NICs or odd DNS, set this:
            // System.setProperty("java.rmi.server.hostname", "127.0.0.1"); 
            // Or your actual LAN IP if clients are on your LAN.

            // 1) Ensure an RMI registry is running on port 1099 (start one if not):
            try {
                LocateRegistry.createRegistry(1099); // starts local registry in this JVM
                System.out.println("Started local RMI registry on port 1099.");
            } catch (RemoteException e) {
                // If it’s already started by another process, we’ll just reuse it
                System.out.println("Local RMI registry already running.");
            }

            // 2) Create & export your remote object
            AccountImpl acct = new AccountImpl("Mr_Blobby");
            Account stub = (Account) UnicastRemoteObject.exportObject(acct, 0);

            // 3) Bind into the local registry
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            registry.rebind("BlobbyAccount", stub);

            System.out.println("\nRegistered account as BlobbyAccount");

            // 4) Keep server alive
            synchronized (AccountServer.class) {
                AccountServer.class.wait();
            }
        } catch (RemoteException re) {
            re.printStackTrace();
            System.err.println("Remote exception while creating/registering: " + re.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
