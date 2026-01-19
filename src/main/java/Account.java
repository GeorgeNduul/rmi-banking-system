
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Account extends Remote {
  String getName() throws RemoteException;
  float getBalance() throws RemoteException;
  void withdraw(float amt) throws RemoteException, InsufficientFundsException;
  void deposit(float amt) throws RemoteException;
  void transfer(float amt, Account src)
      throws RemoteException, InsufficientFundsException;
  float calculateInterest() throws RemoteException;
}
