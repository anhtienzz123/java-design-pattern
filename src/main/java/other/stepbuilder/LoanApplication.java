package other.stepbuilder;

import java.math.BigDecimal;

/**
 * Immutable Loan Application - the product built by the Step Builder.
 * Uses Java record for immutability and conciseness.
 */
public record LoanApplication(
        String applicantName,
        BigDecimal loanAmount,
        int termMonths,
        BigDecimal interestRate,
        String purpose,
        String collateral,
        boolean hasCoSigner
) {
    // Compact constructor for validation
    public LoanApplication {
        if (applicantName == null || applicantName.isBlank()) {
            throw new IllegalArgumentException("Applicant name is required");
        }
        if (loanAmount == null || loanAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be positive");
        }
        if (termMonths <= 0) {
            throw new IllegalArgumentException("Term must be positive");
        }
        if (interestRate == null || interestRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
    }

    @Override
    public String toString() {
        return """
                LoanApplication {
                    applicantName: %s
                    loanAmount: $%s
                    termMonths: %d
                    interestRate: %s%%
                    purpose: %s
                    collateral: %s
                    hasCoSigner: %s
                }""".formatted(
                applicantName,
                loanAmount,
                termMonths,
                interestRate,
                purpose != null ? purpose : "Not specified",
                collateral != null ? collateral : "None",
                hasCoSigner
        );
    }
}
