public class Manager extends AbstractEmployee {
    private static final double FIXED_SALARY = 60000;
    private static final double BONUS_PERCENTAGE = 0.05;

    @Override
    public double getMonthSalary() {
        return FIXED_SALARY + generateRandomIncome() * BONUS_PERCENTAGE;
    }
}

