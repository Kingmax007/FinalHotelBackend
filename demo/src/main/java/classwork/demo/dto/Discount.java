package classwork.demo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double rate;

    @JsonBackReference
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    private List<User> users;

    public Discount(double rate) {
        this.rate = rate;
    }
    @Transient
    public static final Discount NO_DISCOUNT = new Discount(0.0);

    public static Discount getDiscountForUser(User user) {
        switch (user.getLoyalty()) {
            case BRONZE:
                return new Discount(0.05);
            case SILVER:
                return new Discount(0.10);
            case GOLD:
                return new Discount(0.15);
            case PLATINUM:
                return new Discount(0.20);
            default:
                return null;
        }
    }
}