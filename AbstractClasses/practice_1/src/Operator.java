public class Operator extends AbstractEmployee {
    private static final double FIXED_SALARY = 80000;

    @Override
    public double getMonthSalary() {
        return FIXED_SALARY;
    }
}
