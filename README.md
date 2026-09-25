# RMI Banking System
A simple client-server banking application built with **Java RMI** (Remote Method Invocation). A `BankServer` exposes a `Bank` service through which multiple `BankClient` instances can open accounts, deposit, withdraw, transfer funds, and check balances concurrently.

## Features

- **Remote banking operations** — open accounts, deposit, withdraw, transfer, and list accounts over RMI
- **Concurrent-safe accounts** — each account uses a `ReentrantReadWriteLock`, and the bank stores accounts in a `ConcurrentHashMap`
- **Deadlock-free transfers** — server-side transfers lock accounts in a consistent (name-sorted) order to avoid deadlocks
- **Cent-precision balances** — balances are stored internally as integer cents to avoid floating-point rounding errors
- **Custom exception handling** — `InsufficientFundsException` is thrown (and propagated over RMI) when a withdrawal or transfer can't be completed
- **Self-hosting registry** — the server starts its own RMI registry on port `1099`, so you don't need to run `rmiregistry` separately

## Project Structure

```
rmi-banking-system/
├── pom.xml                          # Maven build configuration
├── nbactions.xml                    # NetBeans build/run actions
└── src/main/java/
    ├── Account.java                 # Remote interface for a single account
    ├── AccountImpl.java             # Account implementation (locking, balance logic)
    ├── Bank.java                    # Remote interface for the bank service
    ├── BankImpl.java                # Bank implementation (account registry, transfers)
    ├── BankServer.java              # Entry point: starts the RMI registry and binds the Bank
    ├── BankClient.java              # Command-line client for interacting with the bank
    └── InsufficientFundsException.java
```

## Requirements

- Java 17+ (JDK)
- Maven

## Building

```bash
mvn clean package
```

Or compile directly with `javac` if you prefer not to use Maven:

```bash
javac -d out src/main/java/*.java
```

## Running

### 1. Start the server

```bash
java -cp target\classes BankServer
```

This starts an RMI registry on port `1099` (if one isn't already running) and binds the `Bank` service under the name `BankService`.

### 2. Use the client

In a separate terminal (or several, to simulate concurrent clients):

```bash
# Create/open accounts
java -cp target\classes BankClient 127.0.0.1 open Alice 1000
java -cp target\classes BankClient 127.0.0.1 open Bob 500

# Operate from different clients concurrently
java -cp target\classes BankClient 127.0.0.1 deposit Alice 250
java -cp target\classes BankClient 127.0.0.1 transfer Alice Bob 125.75
java -cp target\classes BankClient 127.0.0.1 balance Alice
java -cp target\classes BankClient 127.0.0.1 balance Bob
java -cp target\classes BankClient 127.0.0.1 list
```

### Available commands

| Command | Usage | Description |
|---|---|---|
| `open` | `open <name> <initial>` | Opens a new account (or returns an existing one) with an initial deposit |
| `deposit` | `deposit <name> <amt>` | Deposits funds into an account |
| `withdraw` | `withdraw <name> <amt>` | Withdraws funds, subject to available balance |
| `transfer` | `transfer <from> <to> <amt>` | Atomically transfers funds between two accounts on the server |
| `balance` | `balance <name>` | Prints the current balance of an account |
| `list` | `list` | Lists all account names known to the bank |

## Design Notes

- **`Bank.transfer` vs `Account.transfer`** — the bank-level transfer (used by the CLI) is the atomic, deadlock-safe path: it locks both accounts server-side before moving funds. The account-level `transfer` method exists for direct client-to-client use but is *not* transactionally atomic across JVM boundaries, and is noted as such in the code.
- **Remote hostname** — if clients will connect from other machines, uncomment and set `java.rmi.server.hostname` in `BankServer` to a reachable IP or DNS name.
- **Interest calculation** — `calculateInterest()` is a simple demonstration (5% of balance) and isn't wired into any scheduled or persistent process.

## License

No license specified.
