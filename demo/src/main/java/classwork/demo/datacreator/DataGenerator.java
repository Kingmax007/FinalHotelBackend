package classwork.demo.datacreator;

import classwork.demo.dto.*;
import classwork.demo.enums.Loyalty;
import classwork.demo.enums.PaymentType;
import classwork.demo.enums.RoomType;
import com.github.javafaker.Faker;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataGenerator {

    private static final Faker faker = new Faker();


    public static List<Room> generateRooms(int numberOfRooms) {
        return IntStream.range(0, numberOfRooms)
                .mapToObj(i -> {
                    Room room = new Room();
                    room.setBasePrice(faker.number().randomDouble(2, 50, 300));
                    room.setAvailable(faker.bool().bool());
                    room.setType(RoomType.values()[faker.number().numberBetween(0, RoomType.values().length)]);

                    int imageId = faker.number().numberBetween(1, 10000); // Simulate image ID generation
                    room.setImageIds(Collections.singletonList(imageId));

                    return room;
                })
                .collect(Collectors.toList());
    }

    public static List<User> generateUsers(int numberOfUsers) {
        return IntStream.range(0, numberOfUsers)
                .mapToObj(i -> {
                    User user = new User();
                    String firstName = faker.name().firstName().toLowerCase();
                    String lastName = faker.name().lastName().toLowerCase();
                    user.setUsername(firstName + "." + lastName);
                    user.setPassword(faker.internet().password());
                    user.setEmail(faker.internet().emailAddress());
                    user.setFirstName(faker.name().firstName());
                    user.setLastName(faker.name().lastName());
                    user.setLoyalty(Loyalty.values()[faker.number().numberBetween(0, Loyalty.values().length)]);
                    user.setDiscount(Discount.getDiscountForUser(user));
                    return user;
                })
                .collect(Collectors.toList());
    }

    public static List<HotelBooking> generateBookings(int numberOfBookings, List<User> users, List<Room> rooms) {
        Faker faker = new Faker();

        return IntStream.range(0, numberOfBookings)
                .mapToObj(i -> {
                    User user = users.get(new Random().nextInt(users.size()));
                    Room room = rooms.get(new Random().nextInt(rooms.size()));

                    HotelBooking booking = new HotelBooking();
                    booking.setUser(user);
                    booking.setRoom(room);
                    booking.setCheckInDateTime(LocalDateTime.now().plusDays(faker.number().numberBetween(1, 30)));
                    booking.setCheckOutDateTime(booking.getCheckInDateTime().plusDays(faker.number().numberBetween(1, 14)));

                    Payment payment = new Payment();
                    payment.setType(PaymentType.values()[faker.number().numberBetween(0, PaymentType.values().length)]);
                    payment.updateAmount(room, user.getDiscount());
                    booking.setPayment(payment);

                    return booking;
                })
                .collect(Collectors.toList());
    }

    private static String generateRandomImageUrl(int width, int height, String architecture, int i) {
        return String.format("https://placeimg.com/%d/%d/%s/%d", width, height, architecture, i);
    }
}