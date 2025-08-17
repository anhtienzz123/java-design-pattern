package structural.facade.bankingsystem;

import java.math.BigDecimal;

// Client code demonstrating the Banking Facade
public class ZMain {
    public static void main(String[] args) {
        // Create the banking facade
        BankingFacade bankingFacade = new BankingFacade();
        
        System.out.println("=== Banking System Demonstration ===\n");
        
        // Demo 1: Check balance
        System.out.println("Demo 1: Checking account balance");
        BigDecimal balance = bankingFacade.checkBalance("john_doe", "password123", "12345");
        System.out.println("Current balance: $" + balance);
        System.out.println();
        
        // Demo 2: Transfer money
        System.out.println("Demo 2: Transferring money");
        boolean transferSuccess = bankingFacade.transferMoney(
            "john_doe", "password123", "12345", "67890", new BigDecimal("100.00")
        );
        System.out.println("Transfer result: " + (transferSuccess ? "Success" : "Failed"));
        System.out.println();
        
        // Demo 3: Check updated balance
        System.out.println("Demo 3: Checking updated balance after transfer");
        balance = bankingFacade.checkBalance("john_doe", "password123", "12345");
        System.out.println("Updated balance: $" + balance);
        System.out.println();
        
        // Demo 4: Cash withdrawal
        System.out.println("Demo 4: Cash withdrawal");
        boolean withdrawalSuccess = bankingFacade.withdrawMoney(
            "jane_smith", "securePass456", "67890", new BigDecimal("200.00")
        );
        System.out.println("Withdrawal result: " + (withdrawalSuccess ? "Success" : "Failed"));
        System.out.println();
        
        // Demo 5: Failed authentication
        System.out.println("Demo 5: Failed authentication attempt");
        balance = bankingFacade.checkBalance("john_doe", "wrongpassword", "12345");
        System.out.println("Balance with wrong password: $" + balance);
        System.out.println();
        
        // Demo 6: Unauthorized account access
        System.out.println("Demo 6: Unauthorized account access attempt");
        balance = bankingFacade.checkBalance("john_doe", "password123", "67890");
        System.out.println("Balance for unauthorized account: $" + balance);
        System.out.println();
        
        // Demo 7: Transaction limit exceeded
        System.out.println("Demo 7: Transaction exceeding daily limit");
        boolean largeTransferSuccess = bankingFacade.transferMoney(
            "john_doe", "password123", "12345", "67890", new BigDecimal("6000.00")
        );
        System.out.println("Large transfer result: " + (largeTransferSuccess ? "Success" : "Failed"));
        System.out.println();
        
        // Demo 8: Insufficient funds
        System.out.println("Demo 8: Insufficient funds scenario");
        boolean insufficientFundsTransfer = bankingFacade.transferMoney(
            "bob_wilson", "myPassword789", "11111", "12345", new BigDecimal("1000.00")
        );
        System.out.println("Insufficient funds transfer result: " + (insufficientFundsTransfer ? "Success" : "Failed"));
        
        /*
        == Sample Output Summary:
        - Demo 1: Successfully checks balance for authenticated user
        - Demo 2: Successfully transfers $100 from account 12345 to 67890
        - Demo 3: Shows updated balance after transfer
        - Demo 4: Successfully withdraws $200 from account 67890
        - Demo 5: Fails due to wrong password
        - Demo 6: Fails due to unauthorized account access
        - Demo 7: Fails due to exceeding transaction limits
        - Demo 8: Fails due to insufficient funds
        
        Each operation involves multiple subsystem calls but appears as a single
        simple operation to the client thanks to the Facade pattern.
        */
    }
}