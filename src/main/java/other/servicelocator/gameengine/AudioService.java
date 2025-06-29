package other.servicelocator.gameengine;

import java.util.HashMap;
import java.util.Map;

/**
 * Audio service responsible for sound playback and music in the game engine
 */
public class AudioService implements GameService {
    private static final String SERVICE_NAME = "AudioService";
    private boolean active = false;
    private float masterVolume = 1.0f;
    private Map<String, Boolean> loadedSounds = new HashMap<>();
    
    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }
    
    @Override
    public void initialize() {
        System.out.println("Initializing " + SERVICE_NAME + "...");
        System.out.println("Setting up audio context and sound buffers");
        active = true;
        masterVolume = 1.0f;
    }
    
    @Override
    public void update(float deltaTime) {
        if (active) {
            // Audio service might update streaming music, fade effects, etc.
        }
    }
    
    @Override
    public void shutdown() {
        System.out.println("Shutting down " + SERVICE_NAME + "...");
        System.out.println("Stopping all sounds and releasing audio resources");
        loadedSounds.clear();
        active = false;
    }
    
    @Override
    public boolean isActive() {
        return active;
    }
    
    public void loadSound(String soundName, String filePath) {
        if (active) {
            System.out.println("Loading sound: " + soundName + " from " + filePath);
            loadedSounds.put(soundName, true);
        }
    }
    
    public void playSound(String soundName) {
        if (active && loadedSounds.containsKey(soundName)) {
            System.out.println("Playing sound: " + soundName + " at volume " + masterVolume);
        } else {
            System.out.println("Sound not loaded: " + soundName);
        }
    }
    
    public void playMusic(String musicFile) {
        if (active) {
            System.out.println("Playing background music: " + musicFile);
        }
    }
    
    public void setMasterVolume(float volume) {
        this.masterVolume = Math.max(0.0f, Math.min(1.0f, volume));
        System.out.println("Master volume set to: " + this.masterVolume);
    }
    
    public float getMasterVolume() {
        return masterVolume;
    }
} 