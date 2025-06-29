package behavioral.memento.gamestate;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * SaveGameManager - Caretaker class
 * 
 * Manages multiple game save states, providing save slot functionality
 * and automatic save features commonly found in games.
 */
public class SaveGameManager {
    private Map<String, GameSaveState> saveSlots;
    private List<GameSaveState> autoSaves;
    private GameSaveState quickSave;
    private final int maxAutoSaves;
    private final int maxSaveSlots;
    
    public SaveGameManager() {
        this(5, 10); // Default: 5 auto-saves, 10 save slots
    }
    
    public SaveGameManager(int maxAutoSaves, int maxSaveSlots) {
        this.saveSlots = new HashMap<>();
        this.autoSaves = new ArrayList<>();
        this.maxAutoSaves = maxAutoSaves;
        this.maxSaveSlots = maxSaveSlots;
        this.quickSave = null;
    }
    
    /**
     * Saves game to a specific slot
     */
    public boolean saveToSlot(String slotName, GameSaveState saveState) {
        if (saveSlots.size() >= maxSaveSlots && !saveSlots.containsKey(slotName)) {
            System.out.println("❌ Cannot save: Maximum save slots (" + maxSaveSlots + ") reached!");
            return false;
        }
        
        saveSlots.put(slotName, saveState);
        System.out.println("💾 Game saved to slot: " + slotName);
        System.out.println("📝 " + saveState.getSaveDescription());
        return true;
    }
    
    /**
     * Loads game from a specific slot
     */
    public GameSaveState loadFromSlot(String slotName) {
        GameSaveState saveState = saveSlots.get(slotName);
        if (saveState != null) {
            System.out.println("📁 Loading game from slot: " + slotName);
            System.out.println("📝 " + saveState.getSaveDescription());
            return saveState;
        } else {
            System.out.println("❌ No save found in slot: " + slotName);
            return null;
        }
    }
    
    /**
     * Creates an auto-save
     */
    public void autoSave(GameSaveState saveState) {
        autoSaves.add(saveState);
        
        // Maintain maximum auto-save count
        if (autoSaves.size() > maxAutoSaves) {
            GameSaveState removed = autoSaves.remove(0);
            System.out.println("🗑️ Removed oldest auto-save: " + removed.getSaveDescription());
        }
        
        System.out.println("⚡ Auto-saved: " + saveState.getSaveDescription());
    }
    
    /**
     * Gets the most recent auto-save
     */
    public GameSaveState getLatestAutoSave() {
        if (!autoSaves.isEmpty()) {
            GameSaveState latest = autoSaves.get(autoSaves.size() - 1);
            System.out.println("📁 Loading latest auto-save: " + latest.getSaveDescription());
            return latest;
        } else {
            System.out.println("❌ No auto-saves available");
            return null;
        }
    }
    
    /**
     * Creates a quick save
     */
    public void quickSave(GameSaveState saveState) {
        this.quickSave = saveState;
        System.out.println("⚡ Quick saved: " + saveState.getSaveDescription());
    }
    
    /**
     * Loads the quick save
     */
    public GameSaveState loadQuickSave() {
        if (quickSave != null) {
            System.out.println("📁 Loading quick save: " + quickSave.getSaveDescription());
            return quickSave;
        } else {
            System.out.println("❌ No quick save available");
            return null;
        }
    }
    
    /**
     * Deletes a save slot
     */
    public boolean deleteSaveSlot(String slotName) {
        GameSaveState removed = saveSlots.remove(slotName);
        if (removed != null) {
            System.out.println("🗑️ Deleted save slot: " + slotName);
            return true;
        } else {
            System.out.println("❌ No save found in slot: " + slotName);
            return false;
        }
    }
    
    /**
     * Clears all auto-saves
     */
    public void clearAutoSaves() {
        autoSaves.clear();
        System.out.println("🗑️ All auto-saves cleared");
    }
    
    /**
     * Clears all saves
     */
    public void clearAllSaves() {
        saveSlots.clear();
        autoSaves.clear();
        quickSave = null;
        System.out.println("🗑️ All saves cleared");
    }
    
    /**
     * Lists all available save slots
     */
    public void listSaveSlots() {
        System.out.println("\n=== Save Slots ===");
        if (saveSlots.isEmpty()) {
            System.out.println("No saves available");
        } else {
            saveSlots.entrySet().stream()
                    .sorted(Map.Entry.<String, GameSaveState>comparingByValue(
                            Comparator.comparing(GameSaveState::getSaveTime).reversed()))
                    .forEach(entry -> {
                        String status = entry.getValue().isCriticalSave() ? " ⚠️" : "";
                        System.out.println("📁 " + entry.getKey() + ": " + 
                                         entry.getValue().getQuickSummary() + status);
                    });
        }
        System.out.println("================\n");
    }
    
    /**
     * Lists all auto-saves
     */
    public void listAutoSaves() {
        System.out.println("\n=== Auto-Saves ===");
        if (autoSaves.isEmpty()) {
            System.out.println("No auto-saves available");
        } else {
            for (int i = autoSaves.size() - 1; i >= 0; i--) {
                GameSaveState save = autoSaves.get(i);
                String status = save.isCriticalSave() ? " ⚠️" : "";
                System.out.println("⚡ Auto-Save " + (i + 1) + ": " + 
                                 save.getQuickSummary() + status);
            }
        }
        System.out.println("=================\n");
    }
    
    /**
     * Shows detailed information about a specific save
     */
    public void showSaveDetails(String slotName) {
        GameSaveState save = saveSlots.get(slotName);
        if (save != null) {
            System.out.println("\n=== Save Details ===");
            System.out.println(save.getDetailedInfo());
            if (save.isCriticalSave()) {
                System.out.println("⚠️ WARNING: Critical save (low health)");
            }
            if (save.isMaxLevel()) {
                System.out.println("⭐ Max level character");
            }
            System.out.println("==================\n");
        } else {
            System.out.println("❌ Save slot '" + slotName + "' not found");
        }
    }
    
    /**
     * Gets save statistics
     */
    public void showSaveStatistics() {
        System.out.println("\n=== Save Statistics ===");
        System.out.println("Save Slots Used: " + saveSlots.size() + "/" + maxSaveSlots);
        System.out.println("Auto-Saves: " + autoSaves.size() + "/" + maxAutoSaves);
        System.out.println("Quick Save: " + (quickSave != null ? "Available" : "None"));
        
        if (!saveSlots.isEmpty()) {
            GameSaveState newest = saveSlots.values().stream()
                    .max(Comparator.comparing(GameSaveState::getSaveTime))
                    .orElse(null);
            if (newest != null) {
                System.out.println("Newest Save: " + newest.getSaveDescription());
            }
        }
        System.out.println("=====================\n");
    }
    
    /**
     * Checks if a save slot exists
     */
    public boolean hasSaveSlot(String slotName) {
        return saveSlots.containsKey(slotName);
    }
    
    /**
     * Gets the number of used save slots
     */
    public int getUsedSlots() {
        return saveSlots.size();
    }
    
    /**
     * Gets the number of auto-saves
     */
    public int getAutoSaveCount() {
        return autoSaves.size();
    }
    
    /**
     * Checks if quick save is available
     */
    public boolean hasQuickSave() {
        return quickSave != null;
    }
} 