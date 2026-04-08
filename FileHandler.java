import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_PATH = "accounts.dat";

    @SuppressWarnings("unchecked")
    public static Map<String, BankAccount> loadAccounts() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, BankAccount>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Warning: Could not load accounts — " + e.getMessage());
            return new HashMap<>();
        }
    }

    public static void saveAccounts(Map<String, BankAccount> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
}
