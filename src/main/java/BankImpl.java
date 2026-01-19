
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BankImpl extends UnicastRemoteObject implements Bank {

    private final Map<String, AccountImpl> accounts = new ConcurrentHashMap<>();
    private final Set<AccountImpl> exported = Collections.newSetFromMap(new IdentityHashMap<>());

    public BankImpl() throws RemoteException {
        super(); // export Bank on an anonymous free port
    }

    @Override
    public Account openAccount(String name, float initialDeposit) throws RemoteException {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name required");
        AccountImpl acct = accounts.computeIfAbsent(name, AccountImpl::new);
        if (initialDeposit > 0f) {
            acct.deposit(initialDeposit);
        }
        exportIfNeeded(acct);
        return acct;
    }

    @Override
    public Account findAccount(String name) throws RemoteException {
        if (name == null) return null;
        AccountImpl acct = accounts.get(name);
        if (acct == null) return null;
        exportIfNeeded(acct);
        return acct;
    }

    @Override
    public List<String> listAccounts() throws RemoteException {
        return new ArrayList<>(accounts.keySet());
    }

    @Override
    public void transfer(String fromName, String toName, float amount)
            throws RemoteException, InsufficientFundsException {
        if (fromName == null || toName == null) throw new IllegalArgumentException("names required");
        if (fromName.equals(toName)) return;
        if (amount <= 0f) throw new IllegalArgumentException("amount must be > 0");

        AccountImpl from = accounts.get(fromName);
        AccountImpl to = accounts.get(toName);
        if (from == null || to == null) throw new RemoteException("One or both accounts not found");

        // Deadlock-free ordering by account name
        AccountImpl first = fromName.compareTo(toName) < 0 ? from : to;
        AccountImpl second = (first == from) ? to : from;

        first.lock.writeLock().lock();
        try {
            second.lock.writeLock().lock();
            try {
                from.withdraw(amount);
                to.deposit(amount);
            } finally {
                second.lock.writeLock().unlock();
            }
        } finally {
            first.lock.writeLock().unlock();
        }
    }

    private synchronized void exportIfNeeded(AccountImpl acct) throws RemoteException {
        if (!exported.contains(acct)) {
            UnicastRemoteObject.exportObject(acct, 0); // random free port (you can fix a port if you need firewall pinning)
            exported.add(acct);
        }
    }
}
