package structural.facade.bankingsystem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// Subsystem: Account management operations
public class AccountService {
    private Map<String, BigDecimal> accounts = new HashMap<>();

    public AccountService() {
        // Initialize with some sample accounts
        accounts.put("12345", new BigDecimal("1000.00"));
        accounts.put("67890", new BigDecimal("2500.00"));
        accounts.put("11111", new BigDecimal("500.00"));
    }

    public boolean validateAccount(String accountNumber) {
        boolean isValid = accounts.containsKey(accountNumber);
        System.out.println("AccountService: Validating account " + accountNumber + " - " + 
                          (isValid ? "Valid" : "Invalid"));
        return isValid;
    }

    public BigDecimal getBalance(String accountNumber) {
        BigDecimal balance = accounts.getOrDefault(accountNumber, BigDecimal.ZERO);
        System.out.println("AccountService: Account " + accountNumber + " balance: $" + balance);
        return balance;
    }

    public boolean debit(String accountNumber, BigDecimal amount) {
        BigDecimal currentBalance = accounts.get(accountNumber);
        if (currentBalance.compareTo(amount) >= 0) {
            accounts.put(accountNumber, currentBalance.subtract(amount));
            System.out.println("AccountService: Debited $" + amount + " from account " + accountNumber);
            return true;
        }
        System.out.println("AccountService: Insufficient funds in account " + accountNumber);
        return false;
    }

    public void credit(String accountNumber, BigDecimal amount) {
        BigDecimal currentBalance = accounts.getOrDefault(accountNumber, BigDecimal.ZERO);
        accounts.put(accountNumber, currentBalance.add(amount));
        System.out.println("AccountService: Credited $" + amount + " to account " + accountNumber);
    }
}