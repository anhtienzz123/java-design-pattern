package behavioral.nullobject.mediaplayer;

public class WaveformVisualizer implements Visualizer {
    private boolean enabled = false;
    
    @Override
    public void startVisualization(String mediaFile) {
        System.out.println("Starting waveform visualization for: " + mediaFile);
        System.out.println("🌊 Displaying audio waveform");
        enabled = true;
    }
    
    @Override
    public void stopVisualization() {
        System.out.println("Stopping waveform visualization");
        enabled = false;
    }
    
    @Override
    public void updateVisualization(int[] frequencyData) {
        if (enabled) {
            System.out.print("🌊 Waveform: ");
            for (int i = 0; i < Math.min(frequencyData.length, 8); i++) {
                int amplitude = frequencyData[i] % 5;
                switch (amplitude) {
                    case 0, 1 -> System.out.print("_ ");
                    case 2 -> System.out.print("~ ");
                    case 3 -> System.out.print("^ ");
                    case 4 -> System.out.print("∩ ");
                }
            }
            System.out.println();
        }
    }
    
    @Override
    public String getVisualizerType() {
        return "Waveform Display";
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}