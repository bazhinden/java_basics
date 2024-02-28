public class Basket {
    private static int totalCount = 0;
    private static int totalCost = 0;

    private String items = "";
    private int totalPrice = 0;
    private int limit;

    public Basket() {
        increaseCount(1);
        items = "Список продуктов:";
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

    public static int getTotalCount() {
        return totalCount;
    }

    public static int getTotalCost() {
        return totalCost;
    }

    public static void increaseCount(int count) {
        totalCount = totalCount + count;
    }

    public static void increaseCost(int cost) {
        totalCost = totalCost + cost;
    }

    public static double calculateAveragePrice() {
        if (totalCount == 0) {
            return 0;
        }
        return (double) totalCost / totalCount;
    }

    public static double calculateAverageBasketCost(int numberOfBaskets) {
        if (numberOfBaskets == 0) {
            return 0;
        }
        return (double) totalCost / numberOfBaskets;
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
            System.out.println("Возникла ошибка :(");
            return;
        }

        items = items + "\n" + name + " - " +
                count + " шт. - " + price + "р.";
        totalPrice = totalPrice + count * price;
        increaseCount(count);
        increaseCost(count * price);
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
            System.out.println("Корзина пуста!");
        } else {
            System.out.println(items);
        }
    }
}
