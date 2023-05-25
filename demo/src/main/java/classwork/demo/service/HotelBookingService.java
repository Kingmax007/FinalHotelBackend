package classwork.demo.service;
import classwork.demo.dto.*;
import classwork.demo.exceptions.ResourceNotFoundException;
import classwork.demo.repositories.HotelBookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class HotelBookingService {
    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @Transactional
    public HotelBooking createBooking(User user, Room room) {
        HotelBooking HotelBooking = new HotelBooking();
        HotelBooking.setUser(user);
        HotelBooking.setRoom(room);

        user.updateLoyalty();
        Discount discount = Discount.getDiscountForUser(user);
        user.setDiscount(discount);

        Payment payment = new Payment();
        payment.updateAmount(room, discount);
        HotelBooking.setPayment(payment);

        return hotelBookingRepository.save(HotelBooking);
    }

    public List<HotelBooking> getAllBookings() {
        return hotelBookingRepository.findAll();
    }

    public Optional<HotelBooking> getBookingById(Long id) {
        return hotelBookingRepository.findById(id);
    }

    public HotelBooking updateBooking(Long id, HotelBooking updatedBooking) {
        return hotelBookingRepository.findById(id)
                .map(booking -> {
                    booking.setUser(updatedBooking.getUser());
                    booking.setRoom(updatedBooking.getRoom());
                    booking.setPayment(updatedBooking.getPayment());
                    return hotelBookingRepository.save(booking);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
    }

    public void deleteBooking(Long id) {
        hotelBookingRepository.deleteById(id);
    }

}