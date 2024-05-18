import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory factory = metadata.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "select s.id, c.id, pl.studentName, pl.courseName, pl.price, pl.subscriptionDate " +
                "from PurchaseList pl " +
                "join Students s on pl.studentName = s.name " +
                "join Courses c on pl.courseName = c.name";

        List<Object[]> objects = session.createQuery(hql).getResultList();
        objects.forEach(o -> {
            LinkedPurchaseList list1 = new LinkedPurchaseList();
            list1.setKey(new LinkedPurchaseListKey(Integer.parseInt(o[0].toString()), Integer.parseInt(o[1]
                    .toString())));
            list1.setStudentName(o[2].toString());
            list1.setCourseName(o[3].toString());
            list1.setPrice(Integer.parseInt(o[4].toString()));
            list1.setSubscriptionDate((Date) o[5]);
            session.save(list1);
        });

        try {
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
