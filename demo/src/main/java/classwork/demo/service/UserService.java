package classwork.demo.service;

import classwork.demo.dto.User;
import classwork.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // Find all rooms
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // Find a User by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Save a User
    public User saveUser(User User) {
        return userRepository.save(User);
    }


    // Delete a User by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}