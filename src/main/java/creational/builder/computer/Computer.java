package creational.builder.computer;

// Product: Computer
public class Computer {
    private final String processor; // Required
    private final int ram; // Required (in GB)
    private final String storage; // Optional
    private final String graphicsCard; // Optional
    private final String operatingSystem; // Optional
    private final boolean hasWifi; // Optional
    private final boolean hasBluetooth; // Optional
    private final int usbPorts; // Optional

    // Private constructor to enforce use of Builder
    private Computer(Builder builder) {
        this.processor = builder.processor;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.operatingSystem = builder.operatingSystem;
        this.hasWifi = builder.hasWifi;
        this.hasBluetooth = builder.hasBluetooth;
        this.usbPorts = builder.usbPorts;
    }

    // Getters (no setters for immutability)
    public String getProcessor() {
        return processor;
    }

    public int getRam() {
        return ram;
    }

    public String getStorage() {
        return storage;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public boolean hasWifi() {
        return hasWifi;
    }

    public boolean hasBluetooth() {
        return hasBluetooth;
    }

    public int getUsbPorts() {
        return usbPorts;
    }

    @Override
    public String toString() {
        return "Computer [processor=" + processor + ", ram=" + ram + "GB, storage=" + storage + 
               ", graphicsCard=" + graphicsCard + ", operatingSystem=" + operatingSystem + 
               ", hasWifi=" + hasWifi + ", hasBluetooth=" + hasBluetooth + 
               ", usbPorts=" + usbPorts + "]";
    }

    // Static nested Builder class
    public static class Builder {
        private final String processor; // Required
        private final int ram; // Required
        private String storage = "256GB SSD"; // Optional with default
        private String graphicsCard = "Integrated"; // Optional with default
        private String operatingSystem = "Windows 11"; // Optional with default
        private boolean hasWifi = true; // Optional with default
        private boolean hasBluetooth = true; // Optional with default
        private int usbPorts = 4; // Optional with default

        // Constructor for required fields
        public Builder(String processor, int ram) {
            this.processor = processor;
            this.ram = ram;
        }

        // Fluent methods for optional fields
        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder graphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder operatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        public Builder hasWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }

        public Builder hasBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }

        public Builder usbPorts(int usbPorts) {
            this.usbPorts = usbPorts;
            return this;
        }

        // Build method to create the Product
        public Computer build() {
            // Validation
            if (processor == null || processor.isEmpty()) {
                throw new IllegalStateException("Processor cannot be empty");
            }
            if (ram <= 0) {
                throw new IllegalStateException("RAM must be greater than 0");
            }
            if (usbPorts < 0) {
                throw new IllegalStateException("USB ports cannot be negative");
            }
            return new Computer(this);
        }
    }
}