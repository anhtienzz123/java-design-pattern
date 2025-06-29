package other.servicelocator.gameengine;

/**
 * Rendering service responsible for graphics rendering in the game engine
 */
public class RenderingService implements GameService {
    private static final String SERVICE_NAME = "RenderingService";
    private boolean active = false;
    private int frameCount = 0;
    
    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }
    
    @Override
    public void initialize() {
        System.out.println("Initializing " + SERVICE_NAME + "...");
        System.out.println("Setting up graphics context and shaders");
        active = true;
        frameCount = 0;
    }
    
    @Override
    public void update(float deltaTime) {
        if (active) {
            frameCount++;
            if (frameCount % 60 == 0) { // Every 60 frames
                System.out.println("Rendering frame " + frameCount + " (deltaTime: " + deltaTime + "ms)");
            }
        }
    }
    
    @Override
    public void shutdown() {
        System.out.println("Shutting down " + SERVICE_NAME + "...");
        System.out.println("Cleaning up graphics resources");
        active = false;
    }
    
    @Override
    public boolean isActive() {
        return active;
    }
    
    // Specific rendering methods
    public void renderMesh(String meshName) {
        if (active) {
            System.out.println("Rendering mesh: " + meshName);
        }
    }
    
    public void setViewMatrix(float[] matrix) {
        if (active) {
            System.out.println("Setting view matrix for camera");
        }
    }
    
    public void clearScreen() {
        if (active) {
            System.out.println("Clearing screen buffer");
        }
    }
    
    public int getFrameCount() {
        return frameCount;
    }
} 