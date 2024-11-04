public class PercentDiscount implements DiscountType{
    @Override
    public double applyDiscount(double initialPrice, double discountAmount) {
        return initialPrice * (1 - discountAmount);
    }
}
