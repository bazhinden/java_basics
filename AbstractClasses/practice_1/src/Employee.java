public interface Employee extends Comparable<Employee> {
    double getMonthSalary();

    @Override
    default int compareTo(Employee o) {
        return Double.compare(getMonthSalary(), o.getMonthSalary());
    }
}
