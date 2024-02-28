public class Main {
    public static void main(String[] args) {

        Processor processor = new Processor(3.5, 4, "Intel", 0.5);
        RAM ram = new RAM("DDR4", 16, 0.2);
        Storage storage = new Storage("SSD", 512, 0.1);
        Display display = new Display(15.6, "IPS", 0.8);
        Keyboard keyboard = new Keyboard("Mechanical", true, 0.6);

        Computer computer = new Computer("Dell", "Inspiron");
        computer.setProcessor(processor);
        computer.setRam(ram);
        computer.setStorage(storage);
        computer.setDisplay(display);
        computer.setKeyboard(keyboard);

        System.out.println(computer);

        System.out.println("Total Weight: " + computer.calculateTotalWeight() + " kg");
    }
}

