package behavioral.nullobject.mediaplayer;

public interface Visualizer {
    void startVisualization(String mediaFile);
    void stopVisualization();
    void updateVisualization(int frequencyData[]);
    String getVisualizerType();
    boolean isEnabled();
}