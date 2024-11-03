public class FixedDiscount implements DiscountType{
    @Override
    public double applyDiscount(double initialPrice, double discountAmount) {
        return initialPrice - discountAmount;
    }
}
