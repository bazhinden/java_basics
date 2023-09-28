package Task2;

public class Main {
    public static void main(String[] args) {
        Dimensions dimensions = new Dimensions(10, 20, 30);

        Cargo cargo = new Cargo(dimensions, 50, "ул. Пушкина, 10", true, "A12345", false);

        Cargo updatedCargo1 = cargo.updateDeliveryAddress("ул. Лермонтова, 5");

        Dimensions newDimensions = new Dimensions(15, 25, 35);
        Cargo updatedCargo2 = cargo.updateDimensions(newDimensions);

        Cargo updatedCargo3 = cargo.updateWeight(60);

        System.out.println(cargo.getDeliveryAddress());
        System.out.println(updatedCargo1.getDeliveryAddress());
        System.out.println(cargo.getDimensions().getVolume());
        System.out.println(updatedCargo2.getDimensions().getVolume());
        System.out.println(cargo.getWeight());
        System.out.println(updatedCargo3.getWeight());
    }
}
