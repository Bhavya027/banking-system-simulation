public class CurrentAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = 10000.0;

    public CurrentAccount(String accountNumber, String holderName, double initialBalance) {
        super(accountNumber, holderName, initialBalance, "CURRENT");
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (balance - amount < -OVERDRAFT_LIMIT)
            throw new IllegalStateException(
                String.format("Overdraft limit of ₹%.2f exceeded.", OVERDRAFT_LIMIT));
        balance -= amount;
    }
}
