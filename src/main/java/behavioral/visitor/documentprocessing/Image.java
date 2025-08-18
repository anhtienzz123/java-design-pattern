package behavioral.visitor.documentprocessing;

public class Image implements DocumentElement {
	private String fileName;
	private String caption;
	private int width;
	private int height;
	private String format;
	
	public Image(String fileName, String caption, int width, int height, String format) {
		this.fileName = fileName;
		this.caption = caption;
		this.width = width;
		this.height = height;
		this.format = format;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getFormat() {
		return format;
	}
	
	public long getPixelCount() {
		return (long) width * height;
	}
	
	public double getAspectRatio() {
		return height == 0 ? 0 : (double) width / height;
	}
	
	@Override
	public void accept(DocumentVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Image{fileName='" + fileName + "', format='" + format + "', " + width + "x" + height + "}";
	}
}