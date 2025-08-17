package structural.facade.bankingsystem;

import java.math.BigDecimal;

// Facade: Simplifies complex banking operations
public class BankingFacade {
    private AccountService accountService;
    private SecurityService securityService;
    private TransactionService transactionService;
    private NotificationService notificationService;

    public BankingFacade() {
        this.accountService = new AccountService();
        this.securityService = new SecurityService();
        this.transactionService = new TransactionService();
        this.notificationService = new NotificationService();
    }

    // Simplified method for checking account balance
    public BigDecimal checkBalance(String username, String password, String accountNumber) {
        System.out.println("=== Banking Facade: Balance Inquiry ===");
        
        // Authenticate user
        if (!securityService.authenticateCustomer(username, password)) {
            System.out.println("Banking Facade: Authentication failed");
            return BigDecimal.ZERO;
        }
        
        // Authorize account access
        if (!securityService.authorizeAccountAccess(username, accountNumber)) {
            System.out.println("Banking Facade: Authorization failed");
            return BigDecimal.ZERO;
        }
        
        // Validate account
        if (!accountService.validateAccount(accountNumber)) {
            System.out.println("Banking Facade: Invalid account");
            return BigDecimal.ZERO;
        }
        
        // Get balance
        BigDecimal balance = accountService.getBalance(accountNumber);
        
        // Log transaction
        securityService.logTransaction(username, "BALANCE_INQUIRY", "Account: " + accountNumber);
        
        System.out.println("Banking Facade: Balance inquiry completed successfully");
        return balance;
    }

    // Simplified method for transferring money
    public boolean transferMoney(String username, String password, String fromAccount, 
                               String toAccount, BigDecimal amount) {
        System.out.println("=== Banking Facade: Money Transfer ===");
        
        // Authenticate user
        if (!securityService.authenticateCustomer(username, password)) {
            System.out.println("Banking Facade: Authentication failed");
            return false;
        }
        
        // Authorize account access
        if (!securityService.authorizeAccountAccess(username, fromAccount)) {
            System.out.println("Banking Facade: Authorization failed for source account");
            return false;
        }
        
        // Validate accounts
        if (!accountService.validateAccount(fromAccount) || !accountService.validateAccount(toAccount)) {
            System.out.println("Banking Facade: Invalid account(s)");
            return false;
        }
        
        // Validate transaction limits
        if (!transactionService.validateTransactionLimits(amount)) {
            System.out.println("Banking Facade: Transaction exceeds limits");
            return false;
        }
        
        // Check sufficient funds
        BigDecimal balance = accountService.getBalance(fromAccount);
        if (balance.compareTo(amount) < 0) {
            System.out.println("Banking Facade: Insufficient funds");
            return false;
        }
        
        // Generate transaction ID
        String transactionId = transactionService.generateTransactionId();
        
        // Process the transfer
        if (transactionService.processTransfer(fromAccount, toAccount, amount)) {
            // Debit from source account
            accountService.debit(fromAccount, amount);
            
            // Credit to destination account
            accountService.credit(toAccount, amount);
            
            // Record transaction
            String details = "Transfer from " + fromAccount + " to " + toAccount + " amount $" + amount;
            transactionService.recordTransaction(transactionId, "TRANSFER", details);
            
            // Log security transaction
            securityService.logTransaction(username, "MONEY_TRANSFER", details);
            
            // Send notification
            notificationService.sendTransactionAlert(username, "Money Transfer", amount);
            
            System.out.println("Banking Facade: Money transfer completed successfully");
            return true;
        }
        
        System.out.println("Banking Facade: Money transfer failed");
        return false;
    }

    // Simplified method for withdrawing money
    public boolean withdrawMoney(String username, String password, String accountNumber, BigDecimal amount) {
        System.out.println("=== Banking Facade: Cash Withdrawal ===");
        
        // Authenticate user
        if (!securityService.authenticateCustomer(username, password)) {
            System.out.println("Banking Facade: Authentication failed");
            return false;
        }
        
        // Authorize account access
        if (!securityService.authorizeAccountAccess(username, accountNumber)) {
            System.out.println("Banking Facade: Authorization failed");
            return false;
        }
        
        // Validate account
        if (!accountService.validateAccount(accountNumber)) {
            System.out.println("Banking Facade: Invalid account");
            return false;
        }
        
        // Validate transaction limits
        if (!transactionService.validateTransactionLimits(amount)) {
            System.out.println("Banking Facade: Transaction exceeds limits");
            return false;
        }
        
        // Process withdrawal
        if (accountService.debit(accountNumber, amount)) {
            // Generate transaction ID
            String transactionId = transactionService.generateTransactionId();
            
            // Record transaction
            String details = "Cash withdrawal from " + accountNumber + " amount $" + amount;
            transactionService.recordTransaction(transactionId, "WITHDRAWAL", details);
            
            // Log security transaction
            securityService.logTransaction(username, "CASH_WITHDRAWAL", details);
            
            // Send notification
            notificationService.sendTransactionAlert(username, "Cash Withdrawal", amount);
            
            System.out.println("Banking Facade: Cash withdrawal completed successfully");
            return true;
        }
        
        System.out.println("Banking Facade: Cash withdrawal failed");
        return false;
    }
}