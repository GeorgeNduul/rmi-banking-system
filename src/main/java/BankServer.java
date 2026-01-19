
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {
    public static void main(String[] args) {
        try {
            // If clients connect from other machines, set this to your server's reachable IP/DNS:
            // System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            // Start a local RMI registry on port 1099 (no need to run rmiregistry separately)
            try {
                LocateRegistry.createRegistry(1099);
                System.out.println("RMI registry started on 1099");
            } catch (Exception e) {
                System.out.println("RMI registry already running on 1099");
            }

            // Create your Bank service implementation
            Bank bank = new BankImpl();

            // Bind it into the registry under a stable name
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            reg.rebind("BankService", bank);
            System.out.println("BankService bound. Ready for clients.");

            // Keep the server alive
            synchronized (BankServer.class) {
                BankServer.class.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
