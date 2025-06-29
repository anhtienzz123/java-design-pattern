# Game State Management - Memento Pattern Implementation

## Overview

This package demonstrates the **Memento Pattern** through a comprehensive game state management system. The implementation showcases how games handle save/load functionality, multiple save slots, auto-saves, and quick saves while maintaining data integrity and providing a rich user experience.

## Pattern Components

### 1. Originator: `GameCharacter`

-   **Purpose**: Represents a game character whose complete state needs to be preserved
-   **Responsibilities**:
    -   Manages character attributes (level, health, experience, gold, equipment, location)
    -   Implements game mechanics (leveling, combat, equipment, movement)
    -   Creates save state mementos containing complete character data
    -   Restores character state from save game mementos
    -   Provides gameplay feedback and status display

### 2. Memento: `GameSaveState`

-   **Purpose**: Immutable container for complete game character state
-   **Characteristics**:
    -   Stores all character attributes with timestamp and unique save ID
    -   Provides metadata for save file management (descriptions, summaries, warnings)
    -   Implements save file comparison and validation methods
    -   Offers utility methods for save file analysis (health percentage, progress tracking)
    -   Maintains package-private access to core data while exposing metadata publicly

### 3. Caretaker: `SaveGameManager`

-   **Purpose**: Manages collections of save states with advanced game-like features
-   **Responsibilities**:
    -   Manages multiple named save slots with capacity limits
    -   Implements auto-save functionality with automatic cleanup
    -   Provides quick save/load for rapid state management
    -   Offers save file organization, listing, and detailed information display
    -   Handles save file deletion and statistics tracking

## Key Features

### Advanced Save Management

-   **Multiple Save Slots**: Named save slots for different game scenarios
-   **Auto-Save System**: Automatic saves with configurable limits and rotation
-   **Quick Save/Load**: Instant save/restore for rapid iteration
-   **Save Metadata**: Rich information about each save including warnings and statistics

### Game State Integrity

-   **Complete State Capture**: All character attributes, equipment, and progress
-   **Immutable Saves**: Prevents accidental modification of save data
-   **Validation**: Health warnings, level tracking, and state analysis
-   **Timestamp Tracking**: Save creation time and unique identifiers

### User Experience Features

-   **Save Descriptions**: Human-readable save file information
-   **Critical Save Warnings**: Alerts for low health or dangerous situations
-   **Progress Tracking**: Experience progress, level achievements, equipment status
-   **Save Statistics**: Usage statistics and save file management information

## Usage Examples

### Basic Save/Load Operations

```java
// Create character and save manager
GameCharacter hero = new GameCharacter("Hero");
SaveGameManager saveManager = new SaveGameManager();

// Make progress and save
hero.gainExperience(100);
hero.equipWeapon("Magic Sword");
saveManager.saveToSlot("checkpoint1", hero.saveGame());

// Continue playing and auto-save
hero.moveToLocation("Boss Arena");
saveManager.autoSave(hero.saveGame());

// Load previous save if needed
GameSaveState checkpoint = saveManager.loadFromSlot("checkpoint1");
if (checkpoint != null) {
    hero.loadGame(checkpoint);
}
```

### Quick Save/Load for Experimentation

```java
// Quick save before risky action
saveManager.quickSave(hero.saveGame());

// Try something dangerous
hero.takeDamage(90);

// Restore if it went wrong
GameSaveState quickSave = saveManager.loadQuickSave();
if (quickSave != null) {
    hero.loadGame(quickSave);
}
```

### Save Management and Analysis

```java
// List all saves
saveManager.listSaveSlots();
saveManager.listAutoSaves();

// Get detailed information
saveManager.showSaveDetails("checkpoint1");
saveManager.showSaveStatistics();

// Check save conditions
if (saveState.isCriticalSave()) {
    System.out.println("Warning: Low health save!");
}
```

## Design Benefits

### 1. **Scalability**

-   Supports multiple characters and save profiles
-   Configurable limits prevent memory overflow
-   Efficient metadata-only operations for browsing

### 2. **Robustness**

-   Immutable save states prevent corruption
-   Graceful handling of missing or invalid saves
-   Automatic cleanup of old auto-saves

### 3. **User Experience**

-   Rich save file information and warnings
-   Multiple save strategies (manual, auto, quick)
-   Easy save file management and organization

### 4. **Extensibility**

-   Easy to add new character attributes
-   Support for custom save metadata
-   Pluggable save storage backends

## Game Mechanics Demonstrated

### Character Progression

-   **Experience System**: Gain XP and level up with stat increases
-   **Equipment System**: Weapon and armor with upgrade paths
-   **Health Management**: Damage, healing, and critical health states
-   **Economic System**: Gold earning, spending, and resource management

### World Interaction

-   **Location System**: Movement between different game areas
-   **Combat Simulation**: Damage dealing and experience rewards
-   **Equipment Upgrades**: Progressive item improvements
-   **Status Tracking**: Complete character state monitoring

## Real-World Applications

This pattern is extensively used in:

### Gaming Industry

-   **RPGs**: Elder Scrolls, Final Fantasy, Witcher series
-   **Strategy Games**: Civilization, Total War series
-   **Simulation Games**: SimCity, Cities: Skylines
-   **Survival Games**: Minecraft, Subnautica

### Other Applications

-   **Development Tools**: IDE project states, debugging snapshots
-   **Document Editors**: Version history, draft management
-   **Financial Software**: Transaction rollback, audit trails
-   **Scientific Applications**: Experiment state management

## Testing the Implementation

Run the `ZMain` class to experience:

1. **Character Creation and Progression**: Level ups, equipment, exploration
2. **Save System Usage**: Manual saves, auto-saves, quick saves
3. **Load Functionality**: Restoration from different save types
4. **Save Management**: Listing, details, statistics, deletion
5. **Edge Cases**: Save limits, missing saves, critical states
6. **Alternative Paths**: Loading saves to explore different outcomes

## Advanced Features Demonstrated

-   **Save File Analysis**: Health warnings, progress tracking
-   **Automatic Management**: Auto-save rotation, capacity limits
-   **Rich Metadata**: Timestamps, descriptions, summaries
-   **State Validation**: Critical health detection, max level tracking
-   **User Interface**: Comprehensive save browsing and management

This implementation showcases how the Memento pattern enables sophisticated state management systems that are both powerful for developers and intuitive for users.
