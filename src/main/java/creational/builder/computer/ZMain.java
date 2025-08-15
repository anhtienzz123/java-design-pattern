package creational.builder.computer;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Builder Pattern - Computer Configuration ===\n");

        // Using Builder directly for custom configuration
        Computer customComputer = new Computer.Builder("AMD Ryzen 5", 16)
                .storage("1TB SSD")
                .graphicsCard("NVIDIA GTX 1660")
                .operatingSystem("Ubuntu 22.04")
                .hasWifi(true)
                .hasBluetooth(false)
                .usbPorts(6)
                .build();
        System.out.println("Custom Computer: " + customComputer);

        // Using Director for predefined configurations
        ComputerDirector director = new ComputerDirector();

        Computer officeComputer = director.constructOfficeComputer();
        System.out.println("\nOffice Computer: " + officeComputer);

        Computer gamingComputer = director.constructGamingComputer();
        System.out.println("\nGaming Computer: " + gamingComputer);

        Computer workstation = director.constructWorkstation();
        System.out.println("\nWorkstation: " + workstation);

        Computer budgetLaptop = director.constructBudgetLaptop();
        System.out.println("\nBudget Laptop: " + budgetLaptop);

        // Demonstrating minimal configuration (only required fields)
        Computer basicComputer = new Computer.Builder("Intel Pentium", 4).build();
        System.out.println("\nBasic Computer (defaults): " + basicComputer);

        // Demonstrating validation
        try {
            Computer invalidComputer = new Computer.Builder("", 0).build();
        } catch (IllegalStateException e) {
            System.out.println("\nValidation Error: " + e.getMessage());
        }
    }
}