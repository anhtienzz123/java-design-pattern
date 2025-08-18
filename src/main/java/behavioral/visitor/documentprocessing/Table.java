package behavioral.visitor.documentprocessing;

import java.util.List;

public class Table implements DocumentElement {
	private List<List<String>> rows;
	private List<String> headers;
	private boolean hasBorder;
	
	public Table(List<String> headers, List<List<String>> rows, boolean hasBorder) {
		this.headers = headers;
		this.rows = rows;
		this.hasBorder = hasBorder;
	}
	
	public List<List<String>> getRows() {
		return rows;
	}
	
	public List<String> getHeaders() {
		return headers;
	}
	
	public boolean hasBorder() {
		return hasBorder;
	}
	
	public int getRowCount() {
		return rows.size();
	}
	
	public int getColumnCount() {
		return headers.size();
	}
	
	public int getCellCount() {
		return getRowCount() * getColumnCount();
	}
	
	@Override
	public void accept(DocumentVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Table{rows=" + rows.size() + ", columns=" + headers.size() + ", hasBorder=" + hasBorder + "}";
	}
}