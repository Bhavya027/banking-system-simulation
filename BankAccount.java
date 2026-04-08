import java.io.Serializable;

public abstract class BankAccount implements Serializable {
    protected String accountNumber;
    protected String holderName;
    protected double balance;
    protected String accountType;

    public BankAccount(String accountNumber, String holderName, double initialBalance, String accountType) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
    }

    // Polymorphic — overridden in subclasses
    public abstract void withdraw(double amount);

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName()    { return holderName; }
    public double getBalance()       { return balance; }
    public String getAccountType()   { return accountType; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Holder: %s | Balance: ₹%.2f",
                accountType, accountNumber, holderName, balance);
    }
}
