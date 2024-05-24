public class Account implements Comparable<Account> {

    private long money;
    private boolean isBlocked;

    public Account(long money) {
        this.money = money;
        this.isBlocked = false;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public int compareTo(Account o) {
        return Long.compare(this.money, o.money);
    }
}