package other.servicelocator.gameengine;

/**
 * Demo class demonstrating the Service Locator pattern in a game engine context
 */
public class ZMain {
    
    public static void main(String[] args) {
        System.out.println("=== Game Engine Service Locator Demo ===\n");
        
        try {
            // Initialize game engine services
            System.out.println("1. Starting Game Engine - Initializing Services:");
            
            // First-time service access (cache miss)
            RenderingService renderer = GameServiceLocator.getRenderingService();
            renderer.clearScreen();
            renderer.setViewMatrix(new float[]{1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1});
            
            AudioService audio = GameServiceLocator.getAudioService();
            audio.loadSound("explosion", "sounds/explosion.wav");
            audio.setMasterVolume(0.8f);
            
            PhysicsService physics = GameServiceLocator.getPhysicsService();
            physics.addPhysicsObject("Player");
            physics.addPhysicsObject("Enemy1");
            physics.setGravity(-9.81f);
            
            GameService input = GameServiceLocator.getInputService();
            
            System.out.println("\nCache size after initialization: " + GameServiceLocator.getCacheSize());
            
            System.out.println("\n2. Subsequent service access (cache hits):");
            RenderingService renderer2 = GameServiceLocator.getRenderingService();
            AudioService audio2 = GameServiceLocator.getAudioService();
            PhysicsService physics2 = GameServiceLocator.getPhysicsService();
            
            System.out.println("\n3. Game Loop Simulation:");
            
            // Simulate game loop
            for (int frame = 0; frame < 5; frame++) {
                float deltaTime = 16.67f; // ~60 FPS
                
                System.out.println("\n--- Frame " + (frame + 1) + " ---");
                
                // Update all services through service locator
                GameServiceLocator.updateAllServices(deltaTime);
                
                // Demonstrate service interactions
                if (frame == 2) {
                    audio.playSound("explosion");
                    renderer.renderMesh("ExplosionEffect");
                    boolean collision = physics.checkCollision("Player", "Enemy1");
                    System.out.println("Collision detected: " + collision);
                }
                
                if (frame == 3) {
                    audio.playMusic("background_music.mp3");
                    renderer.renderMesh("Player");
                    renderer.renderMesh("Enemy1");
                }
            }
            
            System.out.println("\n4. Service Information:");
            System.out.println("Rendering Service - Frame Count: " + renderer.getFrameCount());
            System.out.println("Audio Service - Master Volume: " + audio.getMasterVolume());
            System.out.println("Physics Service - Gravity: " + physics.getGravity());
            System.out.println("All services active: " + 
                (renderer.isActive() && audio.isActive() && physics.isActive() && input.isActive()));
            
            System.out.println("\n5. Shutting Down Game Engine:");
            GameServiceLocator.shutdownAll();
            System.out.println("Cache size after shutdown: " + GameServiceLocator.getCacheSize());
            
        } catch (Exception e) {
            System.err.println("Error in game engine demo: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Game Engine Demo Completed ===");
    }
}