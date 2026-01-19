
# Create/open accounts
java BankClient 127.0.0.1 open Alice 1000
java BankClient 127.0.0.1 open Bob 500

# Operate from different clients concurrently
java BankClient 127.0.0.1 deposit Alice 250
java BankClient 127.0.0.1 transfer Alice Bob 125.75
java BankClient 127.0.0.1 balance Alice
java BankClient 127.0.0.1 balance Bob
java BankClient 127.0.0.1 list
