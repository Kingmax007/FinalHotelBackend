package classwork.demo.dto;

import classwork.demo.enums.Loyalty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Loyalty loyalty = Loyalty.BRONZE;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<HotelBooking> bookings;

    public void updateLoyalty() {
        if (bookings != null) {
            long bookingCount = bookings.stream().count();
            loyalty = bookingCount >= 20 ? Loyalty.PLATINUM :
                    bookingCount >= 15 ? Loyalty.GOLD :
                            bookingCount >= 10 ? Loyalty.SILVER :
                                    bookingCount >= 5 ? Loyalty.BRONZE : null;
            if (loyalty == null) {
                discount = Discount.NO_DISCOUNT;
            }
        }
    }

    public void setLoyalty(Loyalty newLoyalty) {
        if (newLoyalty.ordinal() >= Loyalty.BRONZE.ordinal()) {
            this.loyalty = newLoyalty;
        } else {
            throw new IllegalArgumentException("Loyalty level cannot be below BRONZE");
        }
    }
}