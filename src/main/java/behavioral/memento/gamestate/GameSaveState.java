package behavioral.memento.gamestate;

/**
 * GameSaveState - Memento class
 * 
 * This immutable class stores the complete state of a GameCharacter.
 * It provides controlled access to saved game data while maintaining encapsulation.
 */
public class GameSaveState {
    private final String name;
    private final int level;
    private final int health;
    private final int maxHealth;
    private final int experience;
    private final int gold;
    private final String currentLocation;
    private final String equippedWeapon;
    private final String equippedArmor;
    private final long saveTime;
    private final String saveId;
    
    /**
     * Constructor for creating a game save state
     */
    public GameSaveState(String name, int level, int health, int maxHealth, 
                        int experience, int gold, String currentLocation, 
                        String equippedWeapon, String equippedArmor, long saveTime) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.maxHealth = maxHealth;
        this.experience = experience;
        this.gold = gold;
        this.currentLocation = currentLocation;
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        this.saveTime = saveTime;
        this.saveId = "SAVE_" + name + "_" + saveTime;
    }
    
    // Package-private getters for the originator
    String getName() { return name; }
    int getLevel() { return level; }
    int getHealth() { return health; }
    int getMaxHealth() { return maxHealth; }
    int getExperience() { return experience; }
    int getGold() { return gold; }
    String getCurrentLocation() { return currentLocation; }
    String getEquippedWeapon() { return equippedWeapon; }
    String getEquippedArmor() { return equippedArmor; }
    
    // Public getters for metadata
    public long getSaveTime() { return saveTime; }
    public String getSaveId() { return saveId; }
    
    /**
     * Gets a formatted save description for display
     */
    public String getSaveDescription() {
        java.util.Date date = new java.util.Date(saveTime);
        return String.format("%s - Level %d - %s - %s", 
                           name, level, currentLocation, date.toString());
    }
    
    /**
     * Gets detailed save information
     */
    public String getDetailedInfo() {
        java.util.Date date = new java.util.Date(saveTime);
        return String.format(
            "Save ID: %s%n" +
            "Character: %s (Level %d)%n" +
            "Health: %d/%d%n" +
            "Experience: %d%n" +
            "Gold: %d%n" +
            "Location: %s%n" +
            "Equipment: %s, %s%n" +
            "Save Time: %s",
            saveId, name, level, health, maxHealth, 
            experience, gold, currentLocation, 
            equippedWeapon, equippedArmor, date
        );
    }
    
    /**
     * Gets a quick summary for save slot display
     */
    public String getQuickSummary() {
        return String.format("Lv.%d %s | %s | %dg", 
                           level, name, currentLocation, gold);
    }
    
    /**
     * Calculates the progress percentage to next level
     */
    public int getProgressToNextLevel() {
        return experience % 100;
    }
    
    /**
     * Gets the health percentage
     */
    public double getHealthPercentage() {
        return maxHealth > 0 ? (double) health / maxHealth * 100 : 0;
    }
    
    /**
     * Checks if this is a critical save (low health)
     */
    public boolean isCriticalSave() {
        return getHealthPercentage() < 25;
    }
    
    /**
     * Checks if character was at max level in this save
     */
    public boolean isMaxLevel() {
        return level >= 10; // Assuming max level is 10
    }
    
    @Override
    public String toString() {
        return String.format("GameSaveState{id='%s', character='%s', level=%d, location='%s', time=%s}", 
                           saveId, name, level, currentLocation, new java.util.Date(saveTime));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        GameSaveState that = (GameSaveState) obj;
        return saveTime == that.saveTime && 
               level == that.level && 
               health == that.health && 
               maxHealth == that.maxHealth && 
               experience == that.experience && 
               gold == that.gold && 
               name.equals(that.name) && 
               currentLocation.equals(that.currentLocation) && 
               equippedWeapon.equals(that.equippedWeapon) && 
               equippedArmor.equals(that.equippedArmor);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, level, health, maxHealth, experience, 
                                    gold, currentLocation, equippedWeapon, 
                                    equippedArmor, saveTime);
    }
} 