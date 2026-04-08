public class SavingsAccount extends BankAccount {
    private static final double MIN_BALANCE = 500.0;

    public SavingsAccount(String accountNumber, String holderName, double initialBalance) {
        super(accountNumber, holderName, initialBalance, "SAVINGS");
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (balance - amount < MIN_BALANCE)
            throw new IllegalStateException(
                String.format("Insufficient funds. Savings account must maintain ₹%.2f minimum.", MIN_BALANCE));
        balance -= amount;
    }
}
