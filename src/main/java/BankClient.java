
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankClient {
    public static void main(String[] args) throws Exception {
        // Usage:
        //   java BankClient <host> <command> ...
        // Commands:
        //   open <name> <initial>
        //   deposit <name> <amt>
        //   withdraw <name> <amt>
        //   transfer <from> <to> <amt>
        //   balance <name>
        //   list

        String host = (args.length >= 1) ? args[0] : "127.0.0.1";
        Registry reg = LocateRegistry.getRegistry(host, 1099);
        Bank bank = (Bank) reg.lookup("BankService");

        if (args.length < 2) {
            System.out.println("Commands: open|deposit|withdraw|transfer|balance|list ...");
            return;
        }
        String cmd = args[1].toLowerCase();

        switch (cmd) {
            case "open": {
                String name = args[2];
                float init = Float.parseFloat(args[3]);
                Account acct = bank.openAccount(name, init);
                System.out.println("Opened/returned account for " + acct.getName() + " with balance " + acct.getBalance());
                break;
            }
            case "deposit": {
                String name = args[2];
                float amt = Float.parseFloat(args[3]);
                Account acct = bank.findAccount(name);
                if (acct == null) { System.out.println("No such account: " + name); break; }
                acct.deposit(amt);
                System.out.println("Deposited. New balance: " + acct.getBalance());
                break;
            }
            case "withdraw": {
                String name = args[2];
                float amt = Float.parseFloat(args[3]);
                Account acct = bank.findAccount(name);
                if (acct == null) { System.out.println("No such account: " + name); break; }
                acct.withdraw(amt);
                System.out.println("Withdrew. New balance: " + acct.getBalance());
                break;
            }
            case "transfer": {
                String from = args[2];
                String to = args[3];
                float amt = Float.parseFloat(args[4]);
                bank.transfer(from, to, amt);
                System.out.println("Transferred " + amt + " from " + from + " to " + to);
                break;
            }
            case "balance": {
                String name = args[2];
                Account acct = bank.findAccount(name);
                if (acct == null) { System.out.println("No such account: " + name); break; }
                System.out.println("Balance: " + acct.getBalance());
                break;
            }
            case "list": {
                System.out.println("Accounts: " + bank.listAccounts());
                break;
            }
            default:
                System.out.println("Unknown command.");
        }
    }
}
