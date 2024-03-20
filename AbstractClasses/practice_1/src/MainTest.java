import java.util.List;

public class MainTest {
    public static void main(String[] args) {

        Company company = new Company();

        // Найм 180 операторов
        for (int i = 0; i < 180; i++) {
            company.hire(new Operator());
        }

        // Найм 80 менеджеров
        for (int i = 0; i < 80; i++) {
            company.hire(new Manager());
        }

        // Найм 10 топ-менеджеров
        for (int i = 0; i < 10; i++) {
            company.hire(new TopManager(company.getIncome()));
        }

        List<Employee> topSalaries = company.getTopSalaryStaff(15);
        System.out.println("Список из 15 самых высоких зарплат в компании:");
        for (Employee employee : topSalaries) {
            long salary = Math.round(employee.getMonthSalary());
            System.out.println(salary + " руб.");
        }

        List<Employee> lowestSalaries = company.getLowestSalaryStaff(30);
        System.out.println("\nСписок из 30 самых низких зарплат в компании:");
        for (Employee employee : lowestSalaries) {
            long salary = Math.round(employee.getMonthSalary());
            System.out.println(salary + " руб.");
        }

        int employeesToFire = company.getEmployees().size() / 2;
        for (int i = 0; i < employeesToFire; i++) {
            company.fire(company.getEmployees().get(i));
        }

        topSalaries = company.getTopSalaryStaff(15);
        System.out.println("\nСписок из 15 самых высоких зарплат в компании после увольнения:");
        for (Employee employee : topSalaries) {
            System.out.println(Math.round(employee.getMonthSalary()) + " руб.");
        }

        lowestSalaries = company.getLowestSalaryStaff(30);
        System.out.println("\nСписок из 30 самых низких зарплат в компании после увольнения:");
        for (Employee employee : lowestSalaries) {
            System.out.println(Math.round(employee.getMonthSalary()) + " руб.");
        }
    }
}
