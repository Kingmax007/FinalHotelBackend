package classwork.demo.web;

import classwork.demo.dto.HotelBooking;
import classwork.demo.dto.Room;
import classwork.demo.dto.User;
import classwork.demo.exceptions.ResourceNotFoundException;
import classwork.demo.exceptions.RoomNotFoundException;
import classwork.demo.exceptions.UserNotFoundException;
import classwork.demo.realdto.BookingRequestDTO;
import classwork.demo.repositories.HotelBookingRepository;
import classwork.demo.repositories.RoomRepository;
import classwork.demo.repositories.UserRepository;
import classwork.demo.service.HotelBookingService;
import classwork.demo.service.RoomService;
import classwork.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kings/usersbookings")
public class HotelBookingController {
    @Autowired
    private HotelBookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @Autowired
    private RoomRepository roomRepository;


    @PostMapping
    public ResponseEntity<HotelBooking> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        User user = userRepository.findById(bookingRequestDTO.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Room room = roomRepository.findById(bookingRequestDTO.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        HotelBooking hotelBooking = HotelBooking.builder()
                .user(user)
                .room(room)
                .checkInDateTime(bookingRequestDTO.getCheckInDateTime())
                .checkOutDateTime(bookingRequestDTO.getCheckOutDateTime())
                .build();

        HotelBooking savedBooking = hotelBookingRepository.save(hotelBooking);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    }

    @GetMapping
    public List<HotelBooking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{bookingId}")
    public HotelBooking getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
    }

    @PutMapping("/{bookingId}")
    public HotelBooking updateBooking(@PathVariable Long bookingId, @RequestBody HotelBooking updatedBooking) {
        HotelBooking existingBooking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
        return bookingService.updateBooking(bookingId, updatedBooking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        HotelBooking existingBooking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}