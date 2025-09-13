package strategy;

public class DistanceBasedShipping implements ShippingStrategy {
    private double ratePerKm;

    public DistanceBasedShipping(double ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    @Override
    public double calculateCost(Order order) {
        System.out.println("Calculating with Distance-Based strategy for zone: " + order.getZone());
        return switch (order.getZone()) {
            case "ZoneA" -> 5.0 * ratePerKm;
            case "ZoneB" -> 10.0 * ratePerKm;
            case "ZoneC" -> 15.0 * ratePerKm;
            default -> 20.0 * ratePerKm;
        };
    }
}
