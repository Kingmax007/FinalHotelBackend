package classwork.demo;

import classwork.demo.repositories.HotelBookingRepository;
import classwork.demo.repositories.RoomRepository;
import classwork.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import classwork.demo.datacreator.DataGenerator;
import classwork.demo.dto.Room;
import classwork.demo.dto.User;
import classwork.demo.dto.HotelBooking;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);
		RoomRepository roomRepository = context.getBean(RoomRepository.class);
		HotelBookingRepository bookingRepository = context.getBean(HotelBookingRepository.class);

		List<User> users = DataGenerator.generateUsers(200);
		userRepository.saveAll(users);

		List<Room> rooms = DataGenerator.generateRooms(500);
		roomRepository.saveAll(rooms);

		List<HotelBooking> bookings = DataGenerator.generateBookings(100, users, rooms);
		bookingRepository.saveAll(bookings);

	}
}



