class Item {
    private final String name;
    private final double price;
    private final int quantity;
    private final DiscountType discountType;
    private final double discountAmount;
    private final boolean isGiftCard;

    public Item(String name, double price, int quantity, DiscountType discountType, double discountAmount, boolean isGiftCard) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.isGiftCard = isGiftCard;
    }

    public double applyDiscount() {
        if (discountType == null) {
            return price;
        } else {
            return discountType.applyDiscount(price, discountAmount);
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public boolean isGiftCard(){
        return isGiftCard;
    }
}
