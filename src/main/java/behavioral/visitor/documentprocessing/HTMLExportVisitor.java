package behavioral.visitor.documentprocessing;

public class HTMLExportVisitor implements DocumentVisitor {
	private StringBuilder html = new StringBuilder();
	
	public HTMLExportVisitor() {
		html.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Document Export</title>\n</head>\n<body>\n");
	}
	
	@Override
	public void visit(Paragraph paragraph) {
		html.append("<p style=\"text-align: ").append(paragraph.getAlignment()).append(";\">");
		html.append(escapeHtml(paragraph.getText()));
		html.append("</p>\n");
	}
	
	@Override
	public void visit(Heading heading) {
		String tag = "h" + Math.min(heading.getLevel(), 6);
		html.append("<").append(tag).append(">");
		if (heading.isNumbered()) {
			html.append(heading.getLevel()).append(". ");
		}
		html.append(escapeHtml(heading.getText()));
		html.append("</").append(tag).append(">\n");
	}
	
	@Override
	public void visit(Table table) {
		html.append("<table");
		if (table.hasBorder()) {
			html.append(" border=\"1\" style=\"border-collapse: collapse;\"");
		}
		html.append(">\n");
		
		html.append("  <thead>\n    <tr>\n");
		for (String header : table.getHeaders()) {
			html.append("      <th>").append(escapeHtml(header)).append("</th>\n");
		}
		html.append("    </tr>\n  </thead>\n");
		
		html.append("  <tbody>\n");
		for (var row : table.getRows()) {
			html.append("    <tr>\n");
			for (String cell : row) {
				html.append("      <td>").append(escapeHtml(cell)).append("</td>\n");
			}
			html.append("    </tr>\n");
		}
		html.append("  </tbody>\n</table>\n");
	}
	
	@Override
	public void visit(Image image) {
		html.append("<figure>\n");
		html.append("  <img src=\"").append(escapeHtml(image.getFileName())).append("\"");
		html.append(" width=\"").append(image.getWidth()).append("\"");
		html.append(" height=\"").append(image.getHeight()).append("\"");
		html.append(" alt=\"").append(escapeHtml(image.getCaption())).append("\" />\n");
		
		if (image.getCaption() != null && !image.getCaption().trim().isEmpty()) {
			html.append("  <figcaption>").append(escapeHtml(image.getCaption())).append("</figcaption>\n");
		}
		html.append("</figure>\n");
	}
	
	public String getHTML() {
		return html.toString() + "</body>\n</html>";
	}
	
	private String escapeHtml(String text) {
		if (text == null) return "";
		return text.replace("&", "&amp;")
				   .replace("<", "&lt;")
				   .replace(">", "&gt;")
				   .replace("\"", "&quot;")
				   .replace("'", "&#x27;");
	}
	
	public void printHTML() {
		System.out.println("=== HTML Export ===");
		System.out.println(getHTML());
	}
}