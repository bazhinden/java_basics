
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MimicDate {
    private static final String REDIS_QUEUE_KEY = "userQueue";
    private static final int NUM_USERS = 20;
    private static final Random random = new Random();
    private static double currentTime = 0.0;

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        try {
            RScoredSortedSet<String> userQueue = redisson.getScoredSortedSet(REDIS_QUEUE_KEY);
            if (userQueue.isEmpty()) {
                for (int i = 1; i <= NUM_USERS; i++) {
                    userQueue.add(currentTime++, String.valueOf(i));
                }
            }

            while (true) {
                String currentUser = userQueue.first();
                if (currentUser != null) {
                    System.out.println("-Показ пользователя на главной странице: " + currentUser);

                    userQueue.add(currentTime++, currentUser);
                }
                if (random.nextInt(10) == 0) {
                    List<String> userList = new ArrayList<>(userQueue.readAll());
                    String randomUser = userList.get(random.nextInt(userList.size()));
                    if (randomUser != null) {
                        double minScore = userQueue.firstScore() - 1;
                        userQueue.add(minScore, randomUser);
                        System.out.println(">Пользователь оплатил услугу, его номер: " + randomUser);
                    }
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisson.shutdown();
        }
    }
}