import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String pass = "root";
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select course_name, \n" +
                    "(count(month(subscription_date)) / max(month(subscription_date)))\n" +
                    "as avgCounts from\n" +
                    "purchaseList \n" +
                    "where year(subscription_date) = 2018\n" +
                    "group by  course_name");
            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                Float avgCount = resultSet.getFloat("avgCounts");
                System.out.println(courseName + " - " + avgCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}