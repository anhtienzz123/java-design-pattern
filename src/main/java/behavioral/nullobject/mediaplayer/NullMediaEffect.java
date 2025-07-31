package behavioral.nullobject.mediaplayer;

public class NullMediaEffect implements MediaEffect {
    
    @Override
    public void applyEffect(String mediaFile) {
        // Do nothing - no effect applied
    }
    
    @Override
    public void removeEffect(String mediaFile) {
        // Do nothing - no effect to remove
    }
    
    @Override
    public String getEffectName() {
        return "No Effect";
    }
    
    @Override
    public boolean isActive() {
        return false;
    }
}