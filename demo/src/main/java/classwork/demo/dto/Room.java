package classwork.demo.dto;


import classwork.demo.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private double basePrice;


    private boolean isAvailable;


    @Enumerated(EnumType.STRING)
    private RoomType type;


    @ElementCollection
    private List<Integer> imageIds;

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }
}