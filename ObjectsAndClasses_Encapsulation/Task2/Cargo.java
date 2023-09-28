package Task2;

public class Cargo {
    private final Dimensions dimensions;
    private final double weight;
    private final String deliveryAddress;
    private final boolean canFlip;
    private final String registrationNumber;
    private final boolean isFragile;

    public Cargo(Dimensions dimensions, double weight, String deliveryAddress, boolean canFlip, String registrationNumber, boolean isFragile) {
        this.dimensions = dimensions;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.canFlip = canFlip;
        this.registrationNumber = registrationNumber;
        this.isFragile = isFragile;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public boolean canFlip() {
        return canFlip;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public Cargo updateDeliveryAddress(String newAddress) {
        return new Cargo(dimensions, weight, newAddress, canFlip, registrationNumber, isFragile);
    }

    public Cargo updateDimensions(Dimensions newDimensions) {
        return new Cargo(newDimensions, weight, deliveryAddress, canFlip, registrationNumber, isFragile);
    }

    public Cargo updateWeight(double newWeight) {
        return new Cargo(dimensions, newWeight, deliveryAddress, canFlip, registrationNumber, isFragile);
    }
}