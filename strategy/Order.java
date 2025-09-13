package strategy;

public class Order {
    private double weight;
    private String destination;
    private String zone;

    public Order(double weight, String destination, String zone) {
        this.weight = weight;
        this.destination = destination;
        this.zone = zone;
    }

    public double getWeight() {
        return weight;
    }

    public String getDestination() {
        return destination;
    }

    public String getZone() {
        return zone;
    }
}
