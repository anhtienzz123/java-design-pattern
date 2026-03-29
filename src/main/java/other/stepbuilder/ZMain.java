package other.stepbuilder;

/**
 * Demonstration of the Step Builder Pattern.
 *
 * The Step Builder pattern is a variation of the Builder pattern that:
 * - Enforces a specific order of method calls at COMPILE TIME
 * - Ensures all required fields are set before building
 * - Provides excellent IDE autocompletion guidance
 * - Makes invalid object states impossible to construct
 *
 * Use Cases:
 * - Complex object construction with mandatory ordered steps
 * - APIs where field dependencies exist (B requires A)
 * - Wizard-like object creation flows
 * - When you need compile-time guarantees over runtime validation
 */
public class ZMain {

    public static void main(String[] args) {
        System.out.println("=== Step Builder Pattern Demo ===\n");

        // Example 1: Minimal loan application (required fields only)
        System.out.println("--- Example 1: Minimal Application ---");
        LoanApplication minimalLoan = LoanApplicationStepBuilder
                .newApplication()
                .applicant("John Doe")
                .amount(50_000)
                .termMonths(36)
                .interestRate(5.5)
                .build();

        System.out.println(minimalLoan);

        // Example 2: Full loan application with all optional fields
        System.out.println("\n--- Example 2: Full Application ---");
        LoanApplication fullLoan = LoanApplicationStepBuilder
                .newApplication()
                .applicant("Jane Smith")
                .amount(250_000)
                .termMonths(360)
                .interestRate(4.25)
                .purpose("Home Purchase")
                .collateral("Property at 123 Main St")
                .withCoSigner()
                .build();

        System.out.println(fullLoan);

        // Example 3: Demonstrates compile-time safety
        System.out.println("\n--- Example 3: Compile-Time Safety ---");
        System.out.println("""
                The following code would NOT compile:

                // Missing required steps - compiler error!
                LoanApplication invalid = LoanApplicationStepBuilder
                    .newApplication()
                    .applicant("Bob")
                    .build();  // ERROR: build() not available yet

                // Wrong order - compiler error!
                LoanApplication invalid2 = LoanApplicationStepBuilder
                    .newApplication()
                    .amount(10000)  // ERROR: amount() not available
                    .applicant("Bob")
                    .build();

                This compile-time safety is the key benefit of Step Builder!
                """);

        // Example 4: Partial application with some optional fields
        System.out.println("--- Example 4: Partial Optional Fields ---");
        LoanApplication carLoan = LoanApplicationStepBuilder
                .newApplication()
                .applicant("Alice Johnson")
                .amount(35_000)
                .termMonths(60)
                .interestRate(6.9)
                .purpose("Vehicle Purchase")
                // No collateral or co-signer
                .build();

        System.out.println(carLoan);

        // Example 5: Business loan example
        System.out.println("\n--- Example 5: Business Loan ---");
        LoanApplication businessLoan = LoanApplicationStepBuilder
                .newApplication()
                .applicant("Acme Corporation")
                .amount(500_000)
                .termMonths(120)
                .interestRate(7.5)
                .purpose("Business Expansion")
                .collateral("Commercial Property + Equipment")
                .withCoSigner(false)
                .build();

        System.out.println(businessLoan);

        System.out.println("\n=== Step Builder Pattern Benefits ===");
        System.out.println("""
                1. COMPILE-TIME SAFETY: Invalid construction order is impossible
                2. SELF-DOCUMENTING: IDE shows only valid next steps
                3. IMMUTABLE PRODUCTS: Final objects are thread-safe
                4. FLEXIBLE OPTIONALS: Optional fields can be set in any order
                5. NO RUNTIME ERRORS: Required fields are guaranteed at compile time
                """);
    }
}
