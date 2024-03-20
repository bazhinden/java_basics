import java.util.Random;

public abstract class AbstractEmployee implements Employee {
    protected double generateRandomIncome() {
        return new Random().nextDouble() * (140000 - 115000) + 115000;
    }
}
