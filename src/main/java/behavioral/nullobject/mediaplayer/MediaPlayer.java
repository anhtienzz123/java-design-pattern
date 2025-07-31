package behavioral.nullobject.mediaplayer;

import java.util.Random;

public class MediaPlayer {
    private final MediaEffect audioEffect;
    private final Visualizer visualizer;
    private String currentMedia;
    private boolean isPlaying = false;
    private final Random random = new Random();
    
    public MediaPlayer(MediaEffect audioEffect, Visualizer visualizer) {
        // No null checks needed - we always have implementations (even if null objects)
        this.audioEffect = audioEffect;
        this.visualizer = visualizer;
    }
    
    public void playMedia(String mediaFile) {
        System.out.println("▶️  Playing: " + mediaFile);
        currentMedia = mediaFile;
        isPlaying = true;
        
        // Apply effects and start visualization without null checks
        audioEffect.applyEffect(mediaFile);
        visualizer.startVisualization(mediaFile);
        
        // Simulate playback with some visual updates
        simulatePlayback();
    }
    
    public void stopMedia() {
        if (currentMedia != null) {
            System.out.println("⏹️  Stopping: " + currentMedia);
            isPlaying = false;
            
            // Remove effects and stop visualization without null checks
            audioEffect.removeEffect(currentMedia);
            visualizer.stopVisualization();
            
            currentMedia = null;
        }
    }
    
    public void pauseMedia() {
        if (isPlaying) {
            System.out.println("⏸️  Paused: " + currentMedia);
            isPlaying = false;
        }
    }
    
    public void resumeMedia() {
        if (currentMedia != null && !isPlaying) {
            System.out.println("▶️  Resumed: " + currentMedia);
            isPlaying = true;
        }
    }
    
    public void showCurrentConfiguration() {
        System.out.println("\n🎛️  Current Media Player Configuration:");
        System.out.println("   Audio Effect: " + audioEffect.getEffectName() + 
                         " (Active: " + audioEffect.isActive() + ")");
        System.out.println("   Visualizer: " + visualizer.getVisualizerType() + 
                         " (Enabled: " + visualizer.isEnabled() + ")");
        if (currentMedia != null) {
            System.out.println("   Currently Playing: " + currentMedia);
        }
        System.out.println();
    }
    
    private void simulatePlayback() {
        if (isPlaying) {
            // Generate some mock frequency data for visualization
            int[] frequencyData = new int[12];
            for (int i = 0; i < frequencyData.length; i++) {
                frequencyData[i] = random.nextInt(50) + 10;
            }
            
            // Update visualization - no null check needed
            visualizer.updateVisualization(frequencyData);
        }
    }
}