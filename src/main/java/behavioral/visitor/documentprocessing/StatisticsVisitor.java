package behavioral.visitor.documentprocessing;

import java.util.HashMap;
import java.util.Map;

public class StatisticsVisitor implements DocumentVisitor {
	private int totalElements = 0;
	private int totalWords = 0;
	private int totalCharacters = 0;
	private long totalPixels = 0;
	private Map<String, Integer> elementCounts = new HashMap<>();
	private Map<String, Integer> formatCounts = new HashMap<>();
	private Map<Integer, Integer> headingLevelCounts = new HashMap<>();
	
	@Override
	public void visit(Paragraph paragraph) {
		totalElements++;
		incrementCount(elementCounts, "paragraphs");
		
		if (paragraph.getText() != null) {
			totalWords += paragraph.getWordCount();
			totalCharacters += paragraph.getText().length();
		}
		
		incrementCount(formatCounts, "alignment_" + paragraph.getAlignment());
	}
	
	@Override
	public void visit(Heading heading) {
		totalElements++;
		incrementCount(elementCounts, "headings");
		incrementCount(headingLevelCounts, heading.getLevel());
		
		if (heading.getText() != null) {
			int words = heading.getText().trim().isEmpty() ? 0 : heading.getText().trim().split("\\s+").length;
			totalWords += words;
			totalCharacters += heading.getText().length();
		}
		
		if (heading.isNumbered()) {
			incrementCount(formatCounts, "numbered_headings");
		} else {
			incrementCount(formatCounts, "unnumbered_headings");
		}
	}
	
	@Override
	public void visit(Table table) {
		totalElements++;
		incrementCount(elementCounts, "tables");
		
		int tableWords = 0;
		int tableChars = 0;
		
		for (String header : table.getHeaders()) {
			if (header != null) {
				int words = header.trim().isEmpty() ? 0 : header.trim().split("\\s+").length;
				tableWords += words;
				tableChars += header.length();
			}
		}
		
		for (var row : table.getRows()) {
			for (String cell : row) {
				if (cell != null) {
					int words = cell.trim().isEmpty() ? 0 : cell.trim().split("\\s+").length;
					tableWords += words;
					tableChars += cell.length();
				}
			}
		}
		
		totalWords += tableWords;
		totalCharacters += tableChars;
		
		if (table.hasBorder()) {
			incrementCount(formatCounts, "bordered_tables");
		} else {
			incrementCount(formatCounts, "borderless_tables");
		}
	}
	
	@Override
	public void visit(Image image) {
		totalElements++;
		incrementCount(elementCounts, "images");
		
		if (image.getCaption() != null && !image.getCaption().trim().isEmpty()) {
			int words = image.getCaption().trim().split("\\s+").length;
			totalWords += words;
			totalCharacters += image.getCaption().length();
		}
		
		totalPixels += image.getPixelCount();
		
		if (image.getFormat() != null) {
			incrementCount(formatCounts, "format_" + image.getFormat().toLowerCase());
		}
	}
	
	private void incrementCount(Map<String, Integer> map, String key) {
		map.put(key, map.getOrDefault(key, 0) + 1);
	}
	
	private void incrementCount(Map<Integer, Integer> map, Integer key) {
		map.put(key, map.getOrDefault(key, 0) + 1);
	}
	
	public void printStatistics() {
		System.out.println("=== Document Statistics ===");
		System.out.println("Total Elements: " + totalElements);
		System.out.println("Total Words: " + totalWords);
		System.out.println("Total Characters: " + totalCharacters);
		System.out.println("Total Image Pixels: " + String.format("%,d", totalPixels));
		
		if (totalWords > 0) {
			System.out.println("Average Words per Element: " + String.format("%.1f", (double) totalWords / totalElements));
		}
		
		System.out.println("\nElement Breakdown:");
		elementCounts.forEach((type, count) -> 
			System.out.println("  " + type + ": " + count + " (" + String.format("%.1f%%", 100.0 * count / totalElements) + ")")
		);
		
		if (!headingLevelCounts.isEmpty()) {
			System.out.println("\nHeading Levels:");
			for (int level = 1; level <= 6; level++) {
				int count = headingLevelCounts.getOrDefault(level, 0);
				if (count > 0) {
					System.out.println("  H" + level + ": " + count);
				}
			}
		}
		
		System.out.println("\nFormat Details:");
		formatCounts.forEach((format, count) -> 
			System.out.println("  " + format + ": " + count)
		);
	}
	
	public int getTotalElements() {
		return totalElements;
	}
	
	public int getTotalWords() {
		return totalWords;
	}
	
	public int getTotalCharacters() {
		return totalCharacters;
	}
	
	public long getTotalPixels() {
		return totalPixels;
	}
	
	public Map<String, Integer> getElementCounts() {
		return new HashMap<>(elementCounts);
	}
}