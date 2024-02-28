public class Basket {
    private static int totalItemCount = 0;
    private static int totalCost = 0;
    private static int totalBasketCount = 0;

    private String items = "";
    private int totalPrice = 0;
    private int limit;

    public Basket() {
        increaseTotalItemCount(1);
        increaseTotalBasketCount(1);
        items = "List of products:";
        this.limit = 1000000;
    }

    public Basket(int limit) {
        this();
        this.limit = limit;
    }

    public Basket(String items, int totalPrice) {
        this();
        this.items = this.items + items;
        this.totalPrice = totalPrice;
    }

    public static int getTotalItemCount() {
        return totalItemCount;
    }

    public static int getTotalCost() {
        return totalCost;
    }

    public static int getTotalBasketCount() {
        return totalBasketCount;
    }

    public static void increaseTotalItemCount(int count) {
        totalItemCount = totalItemCount + count;
    }

    public static void increaseTotalCost(int cost) {
        totalCost = totalCost + cost;
    }

    public static void increaseTotalBasketCount(int count) {
        totalBasketCount = totalBasketCount + count;
    }

    public static double calculateAveragePrice() {
        if (totalItemCount == 0) {
            return 0;
        }
        return (double) totalCost / totalItemCount;
    }

    public static double calculateAverageBasketCost() {
        if (totalBasketCount == 0) {
            return 0;
        }
        return (double) totalCost / totalBasketCount;
    }

    public void add(String name, int price) {
        add(name, price, 1);
    }

    public void add(String name, int price, int count) {
        boolean error = false;
        if (contains(name)) {
            error = true;
        }

        if (totalPrice + count * price >= limit) {
            error = true;
        }

        if (error) {
            System.out.println("Error occurred :(");
            return;
        }

        items = items + "\n" + name + " - " +
                count + " шт. - " + price;
        totalPrice = totalPrice + count * price;
        increaseTotalItemCount(count);
        increaseTotalCost(count * price);
    }

    public void clear() {
        items = "";
        totalPrice = 0;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean contains(String name) {
        return items.contains(name);
    }

    public void print(String title) {
        System.out.println(title);
        if (items.isEmpty()) {
            System.out.println("Cart is empty");
        } else {
            System.out.println(items);
        }
    }
}
