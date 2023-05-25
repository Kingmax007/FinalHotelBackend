package classwork.demo.dto;


import classwork.demo.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    public void updateAmount(Room room, Discount discount) {
        double roomPrice = room.getBasePrice();
        switch (room.getType()) {
            case SINGLE:
                break;
            case DOUBLE:
                roomPrice *= 1.2;
                break;
            case SUITE:
                roomPrice *= 1.3;
                break;
            case DELUX:
                roomPrice *= 1.4;
                break;
            case PRESIDENTIAL:
                roomPrice *= 1.8;
                break;
            case  ROYAL_SUITS:
                roomPrice *= 2.5;
                break;
        }
        amount = roomPrice * (1 - discount.getRate());
    }
}