package behavioral.nullobject.mediaplayer;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Null Object Pattern - Media Player Demo ===\n");
        
        // Demonstration 1: Full-featured media player
        System.out.println("1. Media Player with Echo Effect and Spectrum Visualizer:");
        System.out.println("--------------------------------------------------------");
        MediaPlayer premiumPlayer = new MediaPlayer(new EchoEffect(), new SpectrumVisualizer());
        premiumPlayer.showCurrentConfiguration();
        premiumPlayer.playMedia("favorite_song.mp3");
        System.out.println();
        premiumPlayer.pauseMedia();
        premiumPlayer.resumeMedia();
        premiumPlayer.stopMedia();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demonstration 2: Player with different effects
        System.out.println("2. Media Player with Reverb Effect and Waveform Visualizer:");
        System.out.println("------------------------------------------------------------");
        MediaPlayer studioPlayer = new MediaPlayer(new ReverbEffect(), new WaveformVisualizer());
        studioPlayer.showCurrentConfiguration();
        studioPlayer.playMedia("classical_music.wav");
        studioPlayer.stopMedia();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demonstration 3: Basic player with no effects (using null objects)
        System.out.println("3. Basic Media Player (No Effects, No Visualization):");
        System.out.println("----------------------------------------------------");
        MediaPlayer basicPlayer = new MediaPlayer(new NullMediaEffect(), new NullVisualizer());
        basicPlayer.showCurrentConfiguration();
        basicPlayer.playMedia("simple_audio.mp3");
        basicPlayer.pauseMedia();
        basicPlayer.resumeMedia();
        basicPlayer.stopMedia();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demonstration 4: Mixed configuration
        System.out.println("4. Media Player with Effects but No Visualization:");
        System.out.println("-------------------------------------------------");
        MediaPlayer mixedPlayer = new MediaPlayer(new EchoEffect(), new NullVisualizer());
        mixedPlayer.showCurrentConfiguration();
        mixedPlayer.playMedia("podcast.mp3");
        mixedPlayer.stopMedia();
        
        System.out.println("\n=== Key Benefits Demonstrated ===");
        System.out.println("✅ No null checks required in MediaPlayer class");
        System.out.println("✅ Seamless handling of optional features (effects & visualization)");
        System.out.println("✅ Easy configuration switching without code changes");
        System.out.println("✅ Consistent behavior across all configurations");
        System.out.println("✅ Simplified client code - no conditional logic needed");
        
        System.out.println("\n=== Pattern Structure ===");
        System.out.println("MediaEffect Interface → EchoEffect, ReverbEffect, NullMediaEffect");
        System.out.println("Visualizer Interface → SpectrumVisualizer, WaveformVisualizer, NullVisualizer");
        System.out.println("Client (MediaPlayer) → Uses both interfaces without null checks");
    }
}