package behavioral.nullobject.mediaplayer;

public class EchoEffect implements MediaEffect {
    private boolean active = false;
    
    @Override
    public void applyEffect(String mediaFile) {
        System.out.println("Applying echo effect to: " + mediaFile);
        System.out.println("🔊 Echo parameters: Delay=300ms, Feedback=30%, Mix=25%");
        active = true;
    }
    
    @Override
    public void removeEffect(String mediaFile) {
        System.out.println("Removing echo effect from: " + mediaFile);
        active = false;
    }
    
    @Override
    public String getEffectName() {
        return "Echo Effect";
    }
    
    @Override
    public boolean isActive() {
        return active;
    }
}