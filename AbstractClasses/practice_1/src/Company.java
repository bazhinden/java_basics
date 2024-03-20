import java.util.*;

public class Company {
    private List<Employee> employees = new ArrayList<>();
    private double income;

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
        List<Employee> sortedEmployees = new ArrayList<>(employees);
        Collections.sort(sortedEmployees, new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                return Double.compare(emp2.getMonthSalary(), emp1.getMonthSalary());
            }
        });
        return sortedEmployees.subList(0, Math.min(count, sortedEmployees.size()));
    }

    public List<Employee> getLowestSalaryStaff(int count) {
        List<Employee> sortedEmployees = new ArrayList<>(employees);
        Collections.sort(sortedEmployees, new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                return Double.compare(emp1.getMonthSalary(), emp2.getMonthSalary());
            }
        });
        return sortedEmployees.subList(0, Math.min(count, sortedEmployees.size()));
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }
}

