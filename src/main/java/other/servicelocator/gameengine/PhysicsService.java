package other.servicelocator.gameengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Physics service responsible for collision detection and physics simulation
 */
public class PhysicsService implements GameService {
    private static final String SERVICE_NAME = "PhysicsService";
    private boolean active = false;
    private List<String> physicsObjects = new ArrayList<>();
    private float gravity = -9.81f;
    
    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }
    
    @Override
    public void initialize() {
        System.out.println("Initializing " + SERVICE_NAME + "...");
        System.out.println("Setting up physics world and collision detection");
        active = true;
    }
    
    @Override
    public void update(float deltaTime) {
        if (active && !physicsObjects.isEmpty()) {
            System.out.println("Updating physics for " + physicsObjects.size() + " objects");
        }
    }
    
    @Override
    public void shutdown() {
        System.out.println("Shutting down " + SERVICE_NAME + "...");
        System.out.println("Cleaning up physics world");
        physicsObjects.clear();
        active = false;
    }
    
    @Override
    public boolean isActive() {
        return active;
    }
    
    public void addPhysicsObject(String objectName) {
        if (active) {
            physicsObjects.add(objectName);
            System.out.println("Added physics object: " + objectName);
        }
    }
    
    public void removePhysicsObject(String objectName) {
        if (active) {
            physicsObjects.remove(objectName);
            System.out.println("Removed physics object: " + objectName);
        }
    }
    
    public boolean checkCollision(String obj1, String obj2) {
        if (active) {
            System.out.println("Checking collision between " + obj1 + " and " + obj2);
            return Math.random() > 0.7; // Simulate collision detection
        }
        return false;
    }
    
    public void setGravity(float gravity) {
        this.gravity = gravity;
        System.out.println("Gravity set to: " + gravity);
    }
    
    public float getGravity() {
        return gravity;
    }
} 