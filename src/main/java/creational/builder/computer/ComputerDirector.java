package creational.builder.computer;

// Director: ComputerDirector
public class ComputerDirector {
    
    // Construct a basic office computer
    public Computer constructOfficeComputer() {
        return new Computer.Builder("Intel Core i3", 8)
                .storage("512GB SSD")
                .operatingSystem("Windows 11 Pro")
                .usbPorts(6)
                .build();
    }

    // Construct a gaming computer
    public Computer constructGamingComputer() {
        return new Computer.Builder("AMD Ryzen 7", 32)
                .storage("1TB NVMe SSD")
                .graphicsCard("NVIDIA RTX 4070")
                .operatingSystem("Windows 11")
                .hasWifi(true)
                .hasBluetooth(true)
                .usbPorts(8)
                .build();
    }

    // Construct a workstation computer
    public Computer constructWorkstation() {
        return new Computer.Builder("Intel Core i9", 64)
                .storage("2TB NVMe SSD")
                .graphicsCard("NVIDIA RTX 4090")
                .operatingSystem("Windows 11 Pro")
                .hasWifi(true)
                .hasBluetooth(true)
                .usbPorts(10)
                .build();
    }

    // Construct a budget laptop
    public Computer constructBudgetLaptop() {
        return new Computer.Builder("Intel Celeron", 4)
                .storage("128GB eMMC")
                .graphicsCard("Intel UHD Graphics")
                .operatingSystem("Windows 11 S")
                .hasWifi(true)
                .hasBluetooth(false)
                .usbPorts(2)
                .build();
    }
}