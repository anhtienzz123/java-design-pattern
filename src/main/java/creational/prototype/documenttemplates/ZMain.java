package creational.prototype.documenttemplates;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Prototype Pattern: Document Templates ===\n");
        
        // Initialize the document registry
        DocumentRegistry registry = new DocumentRegistry();
        
        // Create and register prototype documents
        System.out.println("1. Setting up document prototypes...\n");
        
        ReportDocument reportTemplate = new ReportDocument();
        reportTemplate.setAuthor("Template Author");
        registry.addPrototype("quarterly-report", reportTemplate);
        
        ProposalDocument proposalTemplate = new ProposalDocument();
        proposalTemplate.setAuthor("Business Development Team");
        registry.addPrototype("project-proposal", proposalTemplate);
        
        ContractDocument contractTemplate = new ContractDocument();
        contractTemplate.setAuthor("Legal Department");
        registry.addPrototype("service-contract", contractTemplate);
        
        // Display available prototypes
        registry.displayAvailablePrototypes();
        System.out.println();
        
        // Clone and customize documents for specific use cases
        System.out.println("2. Creating customized documents from prototypes...\n");
        
        // Create Q2 Financial Report
        Document q2Report = registry.getPrototype("quarterly-report");
        q2Report.setTitle("Q2 2024 Financial Report");
        q2Report.setAuthor("Finance Team");
        ((ReportDocument) q2Report).setDateRange("Q2 2024");
        ((ReportDocument) q2Report).setReportType("Financial Report");
        q2Report.addSection("Risk Assessment");
        
        System.out.println("Created Q2 Financial Report:");
        q2Report.display();
        System.out.println();
        
        // Create Software Development Proposal
        Document softwareProposal = registry.getPrototype("project-proposal");
        softwareProposal.setTitle("E-commerce Platform Development Proposal");
        softwareProposal.setAuthor("Tech Solutions Inc.");
        ((ProposalDocument) softwareProposal).setClientName("ABC Retail Corp");
        ((ProposalDocument) softwareProposal).setProjectDuration("8 months");
        ((ProposalDocument) softwareProposal).setBudget("$150,000");
        softwareProposal.addSection("Technology Stack");
        softwareProposal.addSection("Maintenance and Support");
        
        System.out.println("Created Software Development Proposal:");
        softwareProposal.display();
        System.out.println();
        
        // Create Consulting Service Contract
        Document consultingContract = registry.getPrototype("service-contract");
        consultingContract.setTitle("IT Consulting Services Agreement");
        consultingContract.setAuthor("Legal Affairs");
        ((ContractDocument) consultingContract).setContractType("Consulting Agreement");
        ((ContractDocument) consultingContract).setEffectiveDate("2024-09-01");
        ((ContractDocument) consultingContract).setExpirationDate("2025-08-31");
        consultingContract.addSection("Intellectual Property Rights");
        
        System.out.println("Created IT Consulting Contract:");
        consultingContract.display();
        System.out.println();
        
        // Verify that prototypes remain unchanged
        System.out.println("3. Verifying prototypes are unchanged...\n");
        
        Document originalReport = registry.getPrototype("quarterly-report");
        System.out.println("Original Report Template (unchanged):");
        originalReport.display();
        System.out.println();
        
        // Demonstrate creating multiple instances from same prototype
        System.out.println("4. Creating multiple documents from same prototype...\n");
        
        Document marketingProposal = registry.getPrototype("project-proposal");
        marketingProposal.setTitle("Digital Marketing Campaign Proposal");
        ((ProposalDocument) marketingProposal).setClientName("XYZ Fashion Brand");
        ((ProposalDocument) marketingProposal).setBudget("$75,000");
        
        System.out.println("Created Marketing Proposal (from same prototype):");
        marketingProposal.display();
        
        System.out.println("=== Prototype Pattern Demonstration Complete ===");
    }
}