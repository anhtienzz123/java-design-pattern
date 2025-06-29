package behavioral.memento.gamestate;

/**
 * ZMain - Demonstration of the Memento Pattern with Game State Management
 * 
 * This class demonstrates how the Memento pattern can be used to implement
 * save/load functionality in a game with character progression.
 */
public class ZMain {
    
    public static void main(String[] args) {
        System.out.println("=== Memento Pattern: Game State Management Demo ===\n");
        
        // Create character and save manager
        GameCharacter hero = new GameCharacter("Aragorn");
        SaveGameManager saveManager = new SaveGameManager(3, 5);
        
        // Show initial state
        hero.displayStatus();
        
        System.out.println("--- Starting Adventure ---");
        
        // Save initial state
        saveManager.saveToSlot("start", hero.saveGame());
        saveManager.autoSave(hero.saveGame());
        
        // Character progression - Part 1
        System.out.println("\n--- Early Game Progression ---");
        hero.gainExperience(50);
        hero.gainGold(150);
        hero.moveToLocation("Dark Forest");
        
        // Auto-save after progression
        saveManager.autoSave(hero.saveGame());
        
        // Combat simulation
        System.out.println("\n--- Combat Encounter ---");
        hero.takeDamage(30);
        hero.gainExperience(75);  // Should level up
        hero.gainGold(200);
        
        // Save after first level up
        saveManager.saveToSlot("after_first_levelup", hero.saveGame());
        saveManager.quickSave(hero.saveGame());
        
        hero.displayStatus();
        
        // Equipment upgrade
        System.out.println("\n--- Equipment Upgrade ---");
        hero.spendGold(100);
        hero.equipWeapon("Iron Sword");
        hero.spendGold(150);
        hero.equipArmor("Leather Armor");
        
        // Move to new area
        hero.moveToLocation("Mountain Pass");
        saveManager.saveToSlot("mountain_pass", hero.saveGame());
        
        // More progression
        System.out.println("\n--- Continued Adventure ---");
        hero.gainExperience(125); // Another level up
        hero.takeDamage(40);
        hero.gainGold(300);
        hero.moveToLocation("Ancient Ruins");
        
        saveManager.autoSave(hero.saveGame());
        hero.displayStatus();
        
        // Dangerous situation - take heavy damage
        System.out.println("\n--- Dangerous Boss Fight ---");
        hero.takeDamage(80); // Critical health
        hero.gainExperience(200); // Big reward
        hero.gainGold(500);
        hero.equipWeapon("Enchanted Blade");
        
        // Save in critical state
        saveManager.saveToSlot("critical_health", hero.saveGame());
        
        hero.displayStatus();
        
        // Show all saves
        System.out.println("\n--- Save Management ---");
        saveManager.listSaveSlots();
        saveManager.listAutoSaves();
        saveManager.showSaveStatistics();
        
        // Show details of a specific save
        saveManager.showSaveDetails("after_first_levelup");
        
        System.out.println("\n--- Loading Previous Save ---");
        // Load the mountain pass save (better health)
        GameSaveState mountainSave = saveManager.loadFromSlot("mountain_pass");
        if (mountainSave != null) {
            hero.loadGame(mountainSave);
        }
        
        // Continue from loaded state
        System.out.println("\n--- Alternative Path from Loaded Game ---");
        hero.heal(20);
        hero.gainExperience(100);
        hero.moveToLocation("Wizard Tower");
        hero.spendGold(200);
        hero.equipArmor("Mage Robes");
        
        // Quick save the alternative path
        saveManager.quickSave(hero.saveGame());
        
        hero.displayStatus();
        
        System.out.println("\n--- Testing Quick Save/Load ---");
        // Simulate some risky actions
        hero.takeDamage(60);
        hero.spendGold(300);
        hero.moveToLocation("Trap-filled Dungeon");
        
        System.out.println("Something went wrong! Loading quick save...");
        GameSaveState quickSaveState = saveManager.loadQuickSave();
        if (quickSaveState != null) {
            hero.loadGame(quickSaveState);
        }
        
        System.out.println("\n--- Testing Auto-Save Recovery ---");
        // Show auto-save functionality
        System.out.println("Loading latest auto-save...");
        GameSaveState latestAutoSave = saveManager.getLatestAutoSave();
        if (latestAutoSave != null) {
            hero.loadGame(latestAutoSave);
        }
        
        System.out.println("\n--- Final Save Management Operations ---");
        
        // Create a final save
        hero.moveToLocation("End Game Castle");
        hero.heal(50);
        saveManager.saveToSlot("final_save", hero.saveGame());
        
        // Show final state
        saveManager.listSaveSlots();
        saveManager.showSaveStatistics();
        
        // Test deletion
        System.out.println("Deleting old save...");
        saveManager.deleteSaveSlot("start");
        saveManager.listSaveSlots();
        
        System.out.println("\n--- Edge Case Testing ---");
        
        // Try to save to non-existent slot for loading
        GameSaveState nonExistent = saveManager.loadFromSlot("does_not_exist");
        System.out.println("Loading non-existent save result: " + 
                         (nonExistent == null ? "null (expected)" : "unexpected"));
        
        // Test save slot limits
        System.out.println("\nTesting save slot limits...");
        for (int i = 1; i <= 10; i++) {
            boolean saved = saveManager.saveToSlot("test_slot_" + i, hero.saveGame());
            if (!saved) {
                System.out.println("Hit save limit at slot " + i);
                break;
            }
        }
        
        saveManager.showSaveStatistics();
        
        System.out.println("\n=== Game State Memento Demo Complete ===");
        
        // Final character status
        System.out.println("\n--- Final Character Status ---");
        hero.displayStatus();
    }
} 