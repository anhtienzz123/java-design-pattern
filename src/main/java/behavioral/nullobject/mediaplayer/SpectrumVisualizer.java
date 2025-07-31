package behavioral.nullobject.mediaplayer;

public class SpectrumVisualizer implements Visualizer {
    private boolean enabled = false;
    
    @Override
    public void startVisualization(String mediaFile) {
        System.out.println("Starting spectrum visualization for: " + mediaFile);
        System.out.println("📊 Displaying frequency spectrum bars");
        enabled = true;
    }
    
    @Override
    public void stopVisualization() {
        System.out.println("Stopping spectrum visualization");
        enabled = false;
    }
    
    @Override
    public void updateVisualization(int[] frequencyData) {
        if (enabled) {
            System.out.print("📊 Spectrum: ");
            for (int i = 0; i < Math.min(frequencyData.length, 10); i++) {
                int barHeight = frequencyData[i] / 10;
                System.out.print("█".repeat(Math.max(1, barHeight)) + " ");
            }
            System.out.println();
        }
    }
    
    @Override
    public String getVisualizerType() {
        return "Spectrum Analyzer";
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}