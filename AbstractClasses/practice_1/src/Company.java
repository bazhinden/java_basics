import java.util.*;

public class Company {
    private List<Employee> employees = new ArrayList<>();

    public void hire(Employee employee) {
        employees.add(employee);
    }

    public void fire(Employee employee) {
        employees.remove(employee);
    }

    public double getIncome() {
        double totalIncome = 0;
        for (Employee employee : employees) {
            totalIncome += employee.getMonthSalary();
        }
        return totalIncome;
    }

    public List<Employee> getTopSalaryStaff(int count) {
        Collections.sort(employees);
        Collections.reverse(employees);
        return employees.subList(0, Math.min(count, employees.size()));
    }

    public List<Employee> getLowestSalaryStaff(int count) {
        Collections.sort(employees);
        return employees.subList(0, Math.min(count, employees.size()));
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }
}

