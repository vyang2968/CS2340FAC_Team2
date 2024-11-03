public interface DiscountType {

    /**
     * Reduces a price by a discount after an applied discount
     * @param initialPrice the initial price
     * @param discountAmount the discount's value
     * @return
     */
    double applyDiscount(double initialPrice, double discountAmount);
}