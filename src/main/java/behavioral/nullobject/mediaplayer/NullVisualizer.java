package behavioral.nullobject.mediaplayer;

public class NullVisualizer implements Visualizer {
    
    @Override
    public void startVisualization(String mediaFile) {
        // Do nothing - no visualization
    }
    
    @Override
    public void stopVisualization() {
        // Do nothing - no visualization to stop
    }
    
    @Override
    public void updateVisualization(int[] frequencyData) {
        // Do nothing - no visualization to update
    }
    
    @Override
    public String getVisualizerType() {
        return "No Visualization";
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
}