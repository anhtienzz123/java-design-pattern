package behavioral.memento.gamestate;

/**
 * GameCharacter - Originator class
 * 
 * Represents a game character whose state can be saved and restored.
 * This class manages character attributes and can create mementos for saving progress.
 */
public class GameCharacter {
    private String name;
    private int level;
    private int health;
    private int maxHealth;
    private int experience;
    private int gold;
    private String currentLocation;
    private String equippedWeapon;
    private String equippedArmor;
    
    public GameCharacter(String name) {
        this.name = name;
        this.level = 1;
        this.health = 100;
        this.maxHealth = 100;
        this.experience = 0;
        this.gold = 100;
        this.currentLocation = "Starting Village";
        this.equippedWeapon = "Wooden Sword";
        this.equippedArmor = "Cloth Armor";
    }
    
    /**
     * Character gains experience and potentially levels up
     */
    public void gainExperience(int exp) {
        this.experience += exp;
        System.out.println(name + " gained " + exp + " experience points!");
        
        // Check for level up (every 100 exp)
        int newLevel = (experience / 100) + 1;
        if (newLevel > level) {
            levelUp(newLevel);
        }
    }
    
    /**
     * Character levels up with stat improvements
     */
    private void levelUp(int newLevel) {
        int levelsGained = newLevel - this.level;
        this.level = newLevel;
        
        // Increase stats on level up
        int healthIncrease = levelsGained * 20;
        this.maxHealth += healthIncrease;
        this.health = this.maxHealth; // Full heal on level up
        
        System.out.println("🎉 " + name + " reached level " + level + "!");
        System.out.println("Max health increased by " + healthIncrease + " to " + maxHealth);
    }
    
    /**
     * Character takes damage
     */
    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
        System.out.println(name + " took " + damage + " damage! Health: " + health + "/" + maxHealth);
        
        if (health == 0) {
            System.out.println("💀 " + name + " has been defeated!");
        }
    }
    
    /**
     * Character heals
     */
    public void heal(int amount) {
        this.health = Math.min(maxHealth, this.health + amount);
        System.out.println(name + " healed for " + amount + " HP! Health: " + health + "/" + maxHealth);
    }
    
    /**
     * Character gains gold
     */
    public void gainGold(int amount) {
        this.gold += amount;
        System.out.println(name + " gained " + amount + " gold! Total: " + gold);
    }
    
    /**
     * Character spends gold
     */
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            this.gold -= amount;
            System.out.println(name + " spent " + amount + " gold! Remaining: " + gold);
            return true;
        } else {
            System.out.println(name + " doesn't have enough gold! Need: " + amount + ", Have: " + gold);
            return false;
        }
    }
    
    /**
     * Move to a new location
     */
    public void moveToLocation(String location) {
        this.currentLocation = location;
        System.out.println(name + " moved to " + location);
    }
    
    /**
     * Equip a weapon
     */
    public void equipWeapon(String weapon) {
        this.equippedWeapon = weapon;
        System.out.println(name + " equipped " + weapon);
    }
    
    /**
     * Equip armor
     */
    public void equipArmor(String armor) {
        this.equippedArmor = armor;
        System.out.println(name + " equipped " + armor);
    }
    
    /**
     * Creates a save state memento
     */
    public GameSaveState saveGame() {
        System.out.println("💾 Saving game state for " + name + "...");
        return new GameSaveState(
            name, level, health, maxHealth, experience, gold,
            currentLocation, equippedWeapon, equippedArmor,
            System.currentTimeMillis()
        );
    }
    
    /**
     * Restores state from a save game memento
     */
    public void loadGame(GameSaveState saveState) {
        this.name = saveState.getName();
        this.level = saveState.getLevel();
        this.health = saveState.getHealth();
        this.maxHealth = saveState.getMaxHealth();
        this.experience = saveState.getExperience();
        this.gold = saveState.getGold();
        this.currentLocation = saveState.getCurrentLocation();
        this.equippedWeapon = saveState.getEquippedWeapon();
        this.equippedArmor = saveState.getEquippedArmor();
        
        System.out.println("📁 Loaded game state from " + new java.util.Date(saveState.getSaveTime()));
        displayStatus();
    }
    
    /**
     * Displays the current character status
     */
    public void displayStatus() {
        System.out.println("\n=== Character Status ===");
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Experience: " + experience + " (" + (experience % 100) + "/100 to next level)");
        System.out.println("Gold: " + gold);
        System.out.println("Location: " + currentLocation);
        System.out.println("Weapon: " + equippedWeapon);
        System.out.println("Armor: " + equippedArmor);
        System.out.println("========================\n");
    }
    
    // Getter methods
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getExperience() { return experience; }
    public int getGold() { return gold; }
    public String getCurrentLocation() { return currentLocation; }
    public String getEquippedWeapon() { return equippedWeapon; }
    public String getEquippedArmor() { return equippedArmor; }
    public boolean isAlive() { return health > 0; }
} 