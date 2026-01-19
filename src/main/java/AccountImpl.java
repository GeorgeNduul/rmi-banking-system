
/**
 *   "Java Enterprise in a Nutshell, Third Edition, 
 *    by Jim Farley and William Crawford 
 *    with Prakash Malani, John G. Norman, and Justin Gehtland. 
 *    Copyright 2006 O'Reilly Media, Inc., 0-596-10142-2."
 *    Edited by
 */

import java.rmi.RemoteException;

/**
 * AccountImpl: Implementation of the Account remote interface.
 */

public class AccountImpl implements Account {
  // Our current balance
  private float accBalance = 0;
  // Name on account
  private String accName = "";
  // Create a new account with the given name
  public AccountImpl(String name) throws RemoteException {
    accName = name;
  }
  
  public String getName() throws RemoteException {
    return accName;
  }
  public float getBalance() throws RemoteException {
    return accBalance;
  }
  // Withdraw some funds
  public void withdraw(float amt)
  throws RemoteException, InsufficientFundsException {
    if (accBalance >= amt) {
      accBalance -= amt;
      // Log transaction...
      System.out.println("--> Withdrew " + amt +
                         " from account " + getName());
      System.out.println("    New balance: " + getBalance());
    }
    else {
      throw new InsufficientFundsException("Withdrawal request of " +
                                           amt + " exceeds balance of "
                                           + accBalance);
    }
  }
  
  // Deposit some funds
  public void deposit(float amt) throws RemoteException {
    accBalance += amt;
    // Log transaction...
    System.out.println("--> Deposited " + amt +
                       " into account " + getName());
    System.out.println("    New balance: " + getBalance());
  }
  // Move some funds from another (remote) account into this one
  public void transfer(float amt, Account src)
  throws RemoteException, InsufficientFundsException {
    if (checkTransfer(src, amt)) {
      src.withdraw(amt);
      this.deposit(amt);
      // Log transaction...
      System.out.println("--> Transferred " + amt +
                         " from account " + getName());
      System.out.println("    New balance: " + getBalance());
    }
    else {
      throw new InsufficientFundsException("Source account balance " +
      "is insufficient for transfer");
    }
  }
  
  // Check to see if the transfer is possible, given the source account 
  private boolean checkTransfer(Account src, float amt) {
    boolean approved = false;
    try {
      if (src.getBalance() >= amt) {
        approved = true;
      }
    }
    catch (RemoteException re) {
      // If some remote exception occurred, then the transfer is still
      // compromised, so return false
      approved = false;
    }
    return approved;
  }
        // calculate interest on account balance thread 
  public float calculateInterest() throws RemoteException {
    return accBalance;
  }
}
