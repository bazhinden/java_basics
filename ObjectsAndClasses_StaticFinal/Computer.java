public class Computer {
    private final String vendor;
    private final String name;
    private Processor processor;
    private RAM ram;
    private Storage storage;
    private Display display;
    private Keyboard keyboard;

    public Computer(String vendor, String name) {
        this.vendor = vendor;
        this.name = name;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public RAM getRam() {
        return ram;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public double calculateTotalWeight() {
        double totalWeight = 0;
        if (processor != null) totalWeight += processor.getWeight();
        if (ram != null) totalWeight += ram.getWeight();
        if (storage != null) totalWeight += storage.getWeight();
        if (display != null) totalWeight += display.getWeight();
        if (keyboard != null) totalWeight += keyboard.getWeight();

        return totalWeight;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "vendor='" + vendor + '\'' +
                ", name='" + name + '\'' +
                ", processor=" + processor +
                ", ram=" + ram +
                ", storage=" + storage +
                ", display=" + display +
                ", keyboard=" + keyboard +
                '}';
    }
}