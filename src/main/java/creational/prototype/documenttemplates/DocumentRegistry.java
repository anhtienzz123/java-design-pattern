package creational.prototype.documenttemplates;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DocumentRegistry {
    private final Map<String, Document> prototypes = new HashMap<>();
    
    public void addPrototype(String key, Document prototype) {
        prototypes.put(key, prototype);
    }
    
    public Document getPrototype(String key) {
        Document prototype = prototypes.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype found for key: " + key);
        }
        return prototype.clone();
    }
    
    public boolean hasPrototype(String key) {
        return prototypes.containsKey(key);
    }
    
    public void removePrototype(String key) {
        prototypes.remove(key);
    }
    
    public Set<String> getAvailablePrototypes() {
        return prototypes.keySet();
    }
    
    public void displayAvailablePrototypes() {
        System.out.println("Available Document Prototypes:");
        for (String key : prototypes.keySet()) {
            Document prototype = prototypes.get(key);
            System.out.println("- " + key + " (" + prototype.getType() + "): " + prototype.getTitle());
        }
    }
}