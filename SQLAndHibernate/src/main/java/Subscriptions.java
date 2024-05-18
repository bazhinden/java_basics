import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Subscriptions {
    @EmbeddedId
    private SubscriptionKey id;
    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
