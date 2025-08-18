package behavioral.visitor.documentprocessing;

public class WordCountVisitor implements DocumentVisitor {
	private int totalWords = 0;
	private int paragraphCount = 0;
	private int headingCount = 0;
	private int tableCount = 0;
	private int imageCount = 0;
	
	@Override
	public void visit(Paragraph paragraph) {
		totalWords += paragraph.getWordCount();
		paragraphCount++;
	}
	
	@Override
	public void visit(Heading heading) {
		String text = heading.getText();
		int words = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
		totalWords += words;
		headingCount++;
	}
	
	@Override
	public void visit(Table table) {
		for (String header : table.getHeaders()) {
			int words = header.trim().isEmpty() ? 0 : header.trim().split("\\s+").length;
			totalWords += words;
		}
		
		for (var row : table.getRows()) {
			for (String cell : row) {
				int words = cell.trim().isEmpty() ? 0 : cell.trim().split("\\s+").length;
				totalWords += words;
			}
		}
		tableCount++;
	}
	
	@Override
	public void visit(Image image) {
		if (image.getCaption() != null && !image.getCaption().trim().isEmpty()) {
			int words = image.getCaption().trim().split("\\s+").length;
			totalWords += words;
		}
		imageCount++;
	}
	
	public int getTotalWords() {
		return totalWords;
	}
	
	public int getParagraphCount() {
		return paragraphCount;
	}
	
	public int getHeadingCount() {
		return headingCount;
	}
	
	public int getTableCount() {
		return tableCount;
	}
	
	public int getImageCount() {
		return imageCount;
	}
	
	public void printSummary() {
		System.out.println("=== Word Count Analysis ===");
		System.out.println("Total Words: " + totalWords);
		System.out.println("Paragraphs: " + paragraphCount);
		System.out.println("Headings: " + headingCount);
		System.out.println("Tables: " + tableCount);
		System.out.println("Images: " + imageCount);
		System.out.println("Total Elements: " + (paragraphCount + headingCount + tableCount + imageCount));
	}
}