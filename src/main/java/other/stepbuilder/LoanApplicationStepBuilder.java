package other.stepbuilder;

import java.math.BigDecimal;

/**
 * Step Builder Pattern Implementation for LoanApplication.
 *
 * The Step Builder pattern enforces a specific order of method calls at compile time
 * by using interfaces that return the next step in the building process.
 *
 * Required steps (in order):
 * 1. Applicant Name
 * 2. Loan Amount
 * 3. Term (months)
 * 4. Interest Rate
 *
 * After required steps, optional fields can be set in any order before building.
 */
public final class LoanApplicationStepBuilder {

    private LoanApplicationStepBuilder() {
        // Private constructor to enforce use of static factory method
    }

    // ==================== STEP INTERFACES ====================

    /**
     * Step 1: Set applicant name (required).
     */
    public interface ApplicantStep {
        AmountStep applicant(String name);
    }

    /**
     * Step 2: Set loan amount (required).
     */
    public interface AmountStep {
        TermStep amount(BigDecimal loanAmount);

        default TermStep amount(double loanAmount) {
            return amount(BigDecimal.valueOf(loanAmount));
        }
    }

    /**
     * Step 3: Set loan term in months (required).
     */
    public interface TermStep {
        RateStep termMonths(int months);
    }

    /**
     * Step 4: Set interest rate (required).
     */
    public interface RateStep {
        OptionalStep interestRate(BigDecimal rate);

        default OptionalStep interestRate(double rate) {
            return interestRate(BigDecimal.valueOf(rate));
        }
    }

    /**
     * Optional step: Set optional fields and build.
     * All methods return OptionalStep to allow chaining in any order.
     */
    public interface OptionalStep {
        OptionalStep purpose(String purpose);
        OptionalStep collateral(String collateral);
        OptionalStep withCoSigner();
        OptionalStep withCoSigner(boolean hasCoSigner);
        LoanApplication build();
    }

    // ==================== BUILDER IMPLEMENTATION ====================

    /**
     * Starts the step builder chain.
     *
     * @return the first step (ApplicantStep)
     */
    public static ApplicantStep newApplication() {
        return new Builder();
    }

    /**
     * Internal builder that implements all step interfaces.
     */
    private static final class Builder implements
            ApplicantStep,
            AmountStep,
            TermStep,
            RateStep,
            OptionalStep {

        private String applicantName;
        private BigDecimal loanAmount;
        private int termMonths;
        private BigDecimal interestRate;
        private String purpose;
        private String collateral;
        private boolean hasCoSigner;

        // Step 1: Applicant
        @Override
        public AmountStep applicant(String name) {
            this.applicantName = name;
            return this;
        }

        // Step 2: Amount
        @Override
        public TermStep amount(BigDecimal loanAmount) {
            this.loanAmount = loanAmount;
            return this;
        }

        // Step 3: Term
        @Override
        public RateStep termMonths(int months) {
            this.termMonths = months;
            return this;
        }

        // Step 4: Interest Rate
        @Override
        public OptionalStep interestRate(BigDecimal rate) {
            this.interestRate = rate;
            return this;
        }

        // Optional: Purpose
        @Override
        public OptionalStep purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        // Optional: Collateral
        @Override
        public OptionalStep collateral(String collateral) {
            this.collateral = collateral;
            return this;
        }

        // Optional: Co-signer
        @Override
        public OptionalStep withCoSigner() {
            this.hasCoSigner = true;
            return this;
        }

        @Override
        public OptionalStep withCoSigner(boolean hasCoSigner) {
            this.hasCoSigner = hasCoSigner;
            return this;
        }

        // Build the final product
        @Override
        public LoanApplication build() {
            return new LoanApplication(
                    applicantName,
                    loanAmount,
                    termMonths,
                    interestRate,
                    purpose,
                    collateral,
                    hasCoSigner
            );
        }
    }
}
