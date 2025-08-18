package behavioral.visitor.documentprocessing;

import java.util.ArrayList;
import java.util.List;

public class ValidationVisitor implements DocumentVisitor {
	private List<String> warnings = new ArrayList<>();
	private List<String> errors = new ArrayList<>();
	private int currentHeadingLevel = 0;
	
	@Override
	public void visit(Paragraph paragraph) {
		if (paragraph.getText() == null || paragraph.getText().trim().isEmpty()) {
			warnings.add("Empty paragraph detected");
		}
		
		if (paragraph.getText() != null && paragraph.getText().length() > 1000) {
			warnings.add("Very long paragraph detected (" + paragraph.getText().length() + " characters)");
		}
		
		String alignment = paragraph.getAlignment();
		if (alignment != null && !alignment.matches("left|center|right|justify")) {
			errors.add("Invalid paragraph alignment: " + alignment);
		}
	}
	
	@Override
	public void visit(Heading heading) {
		if (heading.getText() == null || heading.getText().trim().isEmpty()) {
			errors.add("Empty heading detected");
		}
		
		if (heading.getLevel() < 1 || heading.getLevel() > 6) {
			errors.add("Invalid heading level: " + heading.getLevel() + " (must be 1-6)");
		}
		
		if (heading.getLevel() > currentHeadingLevel + 1) {
			warnings.add("Heading level jump detected: from level " + currentHeadingLevel + " to " + heading.getLevel());
		}
		
		currentHeadingLevel = heading.getLevel();
		
		if (heading.getText() != null && heading.getText().length() > 100) {
			warnings.add("Very long heading detected (" + heading.getText().length() + " characters)");
		}
	}
	
	@Override
	public void visit(Table table) {
		if (table.getHeaders() == null || table.getHeaders().isEmpty()) {
			warnings.add("Table without headers detected");
		}
		
		if (table.getRows() == null || table.getRows().isEmpty()) {
			warnings.add("Empty table detected");
		}
		
		if (table.getHeaders() != null && table.getRows() != null) {
			int expectedColumns = table.getHeaders().size();
			for (int i = 0; i < table.getRows().size(); i++) {
				var row = table.getRows().get(i);
				if (row.size() != expectedColumns) {
					errors.add("Row " + (i + 1) + " has " + row.size() + " columns, expected " + expectedColumns);
				}
			}
		}
		
		if (table.getRowCount() > 50) {
			warnings.add("Very large table detected (" + table.getRowCount() + " rows)");
		}
	}
	
	@Override
	public void visit(Image image) {
		if (image.getFileName() == null || image.getFileName().trim().isEmpty()) {
			errors.add("Image without filename detected");
		}
		
		if (image.getWidth() <= 0 || image.getHeight() <= 0) {
			errors.add("Image with invalid dimensions: " + image.getWidth() + "x" + image.getHeight());
		}
		
		if (image.getFormat() == null || image.getFormat().trim().isEmpty()) {
			warnings.add("Image without format specification");
		} else {
			String format = image.getFormat().toLowerCase();
			if (!format.matches("jpg|jpeg|png|gif|bmp|svg|webp")) {
				warnings.add("Unsupported image format: " + image.getFormat());
			}
		}
		
		if (image.getCaption() == null || image.getCaption().trim().isEmpty()) {
			warnings.add("Image without caption: " + image.getFileName());
		}
		
		if (image.getPixelCount() > 10_000_000) {
			warnings.add("Very high resolution image: " + image.getWidth() + "x" + image.getHeight());
		}
	}
	
	public List<String> getWarnings() {
		return warnings;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public boolean hasWarnings() {
		return !warnings.isEmpty();
	}
	
	public void printValidationResults() {
		System.out.println("=== Document Validation Results ===");
		
		if (errors.isEmpty() && warnings.isEmpty()) {
			System.out.println("✓ Document validation passed with no issues!");
			return;
		}
		
		if (!errors.isEmpty()) {
			System.out.println("ERRORS (" + errors.size() + "):");
			for (String error : errors) {
				System.out.println("  ✗ " + error);
			}
		}
		
		if (!warnings.isEmpty()) {
			System.out.println("WARNINGS (" + warnings.size() + "):");
			for (String warning : warnings) {
				System.out.println("  ⚠ " + warning);
			}
		}
		
		System.out.println("Validation completed: " + errors.size() + " errors, " + warnings.size() + " warnings");
	}
}