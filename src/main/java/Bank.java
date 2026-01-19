
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Bank extends Remote {
    Account openAccount(String name, float initialDeposit) throws RemoteException;
    Account findAccount(String name) throws RemoteException;
    List<String> listAccounts() throws RemoteException;

    // Server-side atomic transfer by account names
    void transfer(String fromName, String toName, float amount)
            throws RemoteException, InsufficientFundsException;
}
