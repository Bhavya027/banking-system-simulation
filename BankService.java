import java.util.*;

public class BankService {
    private Map<String, BankAccount> accounts;

    public BankService() {
        this.accounts = FileHandler.loadAccounts();
    }

    public BankAccount createAccount(String type, String number, String holder, double balance) {
        if (accounts.containsKey(number))
            throw new IllegalArgumentException("Account " + number + " already exists.");
        BankAccount account = type.equalsIgnoreCase("SAVINGS")
                ? new SavingsAccount(number, holder, balance)
                : new CurrentAccount(number, holder, balance);
        accounts.put(number, account);
        FileHandler.saveAccounts(accounts);
        return account;
    }

    public void deposit(String number, double amount) {
        getAccount(number).deposit(amount);
        FileHandler.saveAccounts(accounts);
    }

    public void withdraw(String number, double amount) {
        getAccount(number).withdraw(amount);   // polymorphic call
        FileHandler.saveAccounts(accounts);
    }

    public BankAccount getAccount(String number) {
        BankAccount acc = accounts.get(number);
        if (acc == null) throw new NoSuchElementException("Account " + number + " not found.");
        return acc;
    }

    public Collection<BankAccount> getAllAccounts() {
        return accounts.values();
    }
}
