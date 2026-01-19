
import java.rmi.RemoteException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccountImpl implements Account {
    private final String name;
    private long balanceInCents;
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public AccountImpl(String name) {
        this.name = name;
        this.balanceInCents = 0L;
    }

    private static long toCents(float amount) {
        if (amount < 0f) throw new IllegalArgumentException("Amount must be non-negative");
        // Convert cautiously; round to nearest cent
        return Math.round(amount * 100.0f);
    }

    private static float toFloat(long cents) {
        return cents / 100.0f;
    }

    @Override
    public String getName() throws RemoteException {
        lock.readLock().lock();
        try {
            return name;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public float getBalance() throws RemoteException {
        lock.readLock().lock();
        try {
            return toFloat(balanceInCents);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void withdraw(float amt) throws RemoteException, InsufficientFundsException {
        final long cents = toCents(amt);
        lock.writeLock().lock();
        try {
            if (cents > balanceInCents) {
                throw new InsufficientFundsException("Insufficient funds: need " + amt + ", have " + toFloat(balanceInCents));
            }
            balanceInCents -= cents;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void deposit(float amt) throws RemoteException {
        final long cents = toCents(amt);
        lock.writeLock().lock();
        try {
            balanceInCents += cents;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void transfer(float amt, Account src) throws RemoteException, InsufficientFundsException {
        // This is safe if src == this (self-transfer), but for two different accounts
        // in different JVMs/hosts, you cannot take both locks atomically here.
        // We implement a simple 2-step remote call which is NOT transactionally atomic
        // across JVM boundaries. For true atomicity, use the Bank service in Path B.
        src.withdraw(amt);  // may throw InsufficientFundsException
        try {
            this.deposit(amt);
        } catch (RemoteException re) {
            // You'd need compensation logic here (e.g., deposit back to src) in production.
            // For assignment purposes we keep it simple.
            throw re;
        }
    }

    @Override
    public float calculateInterest() throws RemoteException {
        // Simple demonstration: 5% of balance
        lock.readLock().lock();
        try {
            return toFloat(Math.round(balanceInCents * 0.05));
        } finally {
            lock.readLock().unlock();
        }
    }
}
