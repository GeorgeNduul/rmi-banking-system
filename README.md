# RMI Banking System
A simple client-server banking application built with **Java RMI** (Remote Method Invocation). A `BankServer` exposes a `Bank` service through which multiple `BankClient` instances can open accounts, deposit, withdraw, transfer funds, and check balances concurrently.
## What is RMI?

Remote Method Invocation (RMI) is a Java technology that allows
a program running in one Java Virtual Machine (JVM) to invoke methods on objects running
in another JVM across a network.

In this project:

- BankServer hosts the Bank service
- Clients connect through the RMI Registry
- Clients remotely invoke methods such as:
    - openAccount()
    - deposit()
    - withdraw()
    - transfer()
 ## Concurrency Design

The banking service must support multiple client requests simultaneously.

### ConcurrentHashMap

Accounts are stored in a ConcurrentHashMap.

Benefits:

- Thread-safe access
- Better scalability than synchronized collections
- Supports concurrent readers and writers

### ReentrantReadWriteLock

Each account uses a ReentrantReadWriteLock.

Benefits:

- Multiple threads can read balances concurrently
- Writes remain exclusive
- Improves throughput under heavy read workloads
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
### cd to project folder
### 1. Start the server

```bash
java -cp target\classes BankServer
```
<img width="1091" height="112" alt="image" src="https://github.com/user-attachments/assets/d06dd1da-7abd-42e1-93fe-2c1c21fcf91c" />

This starts an RMI registry on port `1099` (if one isn't already running) and binds the `Bank` service under the name `BankService`.

### 2. Use the client

In a separate terminal (or several, to simulate concurrent clients):

```bash
# Create/open accounts
java -cp target\classes BankClient 127.0.0.1 open Alice 1000
```
<img width="1392" height="47" alt="image" src="https://github.com/user-attachments/assets/12053cc8-1ff1-419f-bce4-f3dff5f1a498" />


java -cp target\classes BankClient 127.0.0.1 open Bob 500
```
<img width="1327" height="47" alt="image" src="https://github.com/user-attachments/assets/567b24ac-b11c-49e1-9375-ea4ab18ed9bd" />

# Operate from different clients concurrently
java -cp target\classes BankClient 127.0.0.1 deposit Alice 250
```
<img width="1467" height="327" alt="image" src="https://github.com/user-attachments/assets/ccffb239-d4d6-421c-ada8-2f28b9c69133" />
java -cp target\classes BankClient 127.0.0.1 transfer Alice Bob 125.75
```
<img width="1442" height="40" alt="image" src="https://github.com/user-attachments/assets/ea935527-b699-404c-b1ed-03e4b4b5521f" />
java -cp target\classes BankClient 127.0.0.1 balance Alice
```
<img width="1347" height="42" alt="image" src="https://github.com/user-attachments/assets/194bbefe-a3d3-4c43-9c8b-0def8cbd0ab0" />
```
java -cp target\classes BankClient 127.0.0.1 balance Bob
```
<img width="1287" height="42" alt="image" src="https://github.com/user-attachments/assets/6408e8ce-4f68-4f18-9ba2-348b64e9be71" />
java -cp target\classes BankClient 127.0.0.1 list
```
<img width="1212" height="46" alt="image" src="https://github.com/user-attachments/assets/3dd3199e-4052-41ab-aab9-fa311c90a759" />


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
## Skills Demonstrated

- Object-Oriented Programming
- Distributed Systems
- Client-Server Architecture
- Network Programming
- Concurrency
- Thread Safety
- Exception Handling
- Maven Build Management
- Unit Testing
- Version Control with Git
## Exception Handling

The project uses custom exceptions such as:

- InsufficientFundsException

This exception is propagated across RMI boundaries when
a withdrawal or transfer exceeds the available balance.
## License

No license specified.
