/*
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.ConnectException;

public class AccountClient {
    public static void main(String[] args) {
        // Usage: java AccountClient <host> [port]
        String host = (args.length >= 1) ? args[0] : "127.0.0.1"; // default to localhost
        int port = 1099; // default RMI registry port
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid port; falling back to 1099.");
                port = 1099;
            }
        }

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            Account stub = (Account) registry.lookup("BlobbyAccount");

            // Perform remote calls
            stub.deposit(12000);

            System.out.println("Deposited 12,000 into account owned by " + stub.getName());
            System.out.println("Balance now totals: " + stub.getBalance());
        } catch (NotBoundException nbe) {
            System.err.println("Name not bound in registry at " + host + ":" + port + " — " + nbe.getMessage());
            System.err.println("Tip: Ensure the server rebounded 'BlobbyAccount' and you’re using the right host/port.");
        } catch (RemoteException re) {
            System.err.println("Remote exception during lookup or call: " + re.getClass().getName() + " — " + re.getMessage());
            re.printStackTrace();
        }
    }
}
*/