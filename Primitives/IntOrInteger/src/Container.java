public class Container {
    private Integer count;

    public void addCount(int value) {
        if (count == null) {
            count = 0;
        }
        count = count + value;
    }

    public int getCount() {
        if (count == null) {
            return 0;
        }
        return count;
    }
}
