public class TopManager extends AbstractEmployee {
    private static final double FIXED_SALARY = 100_000;
    private static final double BONUS_MULTIPLIER = 1.5;
    private static final double MIN_COMPANY_INCOME = 10_000_000;

    private double companyIncome;

    public TopManager(double companyIncome) {
        this.companyIncome = companyIncome;
    }

    @Override
    public double getMonthSalary() {
        if (companyIncome > MIN_COMPANY_INCOME) {
            return FIXED_SALARY * BONUS_MULTIPLIER;
        } else {
            return FIXED_SALARY;
        }
    }
}
