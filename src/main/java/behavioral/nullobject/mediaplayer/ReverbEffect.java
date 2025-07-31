package behavioral.nullobject.mediaplayer;

public class ReverbEffect implements MediaEffect {
    private boolean active = false;
    
    @Override
    public void applyEffect(String mediaFile) {
        System.out.println("Applying reverb effect to: " + mediaFile);
        System.out.println("🎵 Reverb parameters: Room size=Large, Damping=0.5, Wet/Dry=40%");
        active = true;
    }
    
    @Override
    public void removeEffect(String mediaFile) {
        System.out.println("Removing reverb effect from: " + mediaFile);
        active = false;
    }
    
    @Override
    public String getEffectName() {
        return "Reverb Effect";
    }
    
    @Override
    public boolean isActive() {
        return active;
    }
}