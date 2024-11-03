import java.util.List;

public class Order {
    private List<Item> items;
    private String customerName;
    private String customerEmail;
    private static final double GIFT_CARD_AMT = 10.0;
    private static final double MIN_ORDER_TOTAL = 0.0;
    private static final double BULK_DISCOUNT_MIN = 100.0;
    private static final double BULK_DISCOUNT_AMT = 0.10;
    private static final double PERCENTAGE_DIVISOR = 100;
    private static final double NO_DISCOUNT_AMT = 0;

    public Order(List<Item> items, String customerName, String customerEmail) {
        this.items = items;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public double calculateTotalPrice() {
    	double total = 0.0;
    	for (Item item : items) {
        	double price = item.getPrice();

        	total += (price - calculateDiscount(item)) * item.getQuantity();

       	    if (item instanceof TaxableItem taxableItem) {
                total += calculateTax(taxableItem.getTaxRate(), item.getPrice());
            }
        }

        total = applyGiftCardIfNecessary(total, hasGiftCard());
        total -= applyBulkDiscountIfNecessary(total);

    	return total;
    }

    private double calculateDiscount(Item item) {
        if (item.getDiscountType() == DiscountType.PERCENTAGE) {
            return item.getDiscountAmount() * item.getPrice();
        } else if (item.getDiscountType() == DiscountType.AMOUNT) {
            return item.getDiscountAmount();
        } else {
            return NO_DISCOUNT_AMT;
        }
    }

    private double calculateTax(double taxRate, double price) {
        return (taxRate / PERCENTAGE_DIVISOR) * price;
    }

    private double applyGiftCardIfNecessary(double total, boolean hasGiftCard) {
        return hasGiftCard ? Math.max(MIN_ORDER_TOTAL, total - GIFT_CARD_AMT) : total;
    }

    private double applyBulkDiscountIfNecessary(double total) {
        return (total > BULK_DISCOUNT_AMT) ? total * BULK_DISCOUNT_AMT : total;
    }

    public void sendConfirmationEmail() {
        String thanksMessage = String.format("Thank you for your order, %s!\n\n", customerName);
        String orderHeader = "Your order details\n";

        StringBuilder builder = new StringBuilder(thanksMessage);
        builder.append(orderHeader);

        for (Item item : items) {
            String itemInfo = String.format("%s - %f\n", item.getName(), item.getPrice());
            builder.append(itemInfo);
        }

        String totalInfo = String.format("Total: %f", calculateTotalPrice());
        builder.append(totalInfo);

        EmailSender.sendEmail(customerEmail, "Order Confirmation", builder.toString());
    }


    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean hasGiftCard() {
        for (Item item : items) {
            if (item.isGiftCard()) {
                return true;
            }
        }
        return false;
    }

   public void printOrder() {
        System.out.println("Order Details:");
        for (Item item : items) {
            System.out.println(item.getName() + " - " + item.getPrice());
        }
   }

   public void addItemsFromAnotherOrder(Order otherOrder) {
        for (Item item : otherOrder.getItems()) {
            items.add(item);
        }
   }

}

