package behavioral.nullobject.mediaplayer;

public interface MediaEffect {
    void applyEffect(String mediaFile);
    void removeEffect(String mediaFile);
    String getEffectName();
    boolean isActive();
}