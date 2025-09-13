package strategy;

public class WeightBasedShipping implements ShippingStrategy {
    private double ratePerKg;

    public WeightBasedShipping(double ratePerKg) {
        this.ratePerKg = ratePerKg;
    }

    @Override
    public double calculateCost(Order order) {
        double cost = order.getWeight() * ratePerKg;
        System.out.println("Calculating with Weight Based strategy ($" + ratePerKg + " per kg): Total Cost = $" + cost);
        return cost;
    }
}
