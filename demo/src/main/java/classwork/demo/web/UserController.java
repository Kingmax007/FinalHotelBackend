package classwork.demo.web;

import classwork.demo.dto.Discount;
import classwork.demo.dto.User;
import classwork.demo.enums.Loyalty;
import classwork.demo.exceptions.ResourceNotFoundException;
import classwork.demo.realdto.UserCreateDTO;
import classwork.demo.repositories.UserRepository;
import classwork.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "http://localhost:3000")

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return ResponseEntity.ok().body(user);
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        User user = convertToUser(userCreateDTO);
        user.setDiscount(Discount.getDiscountForUser(user));
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    private User convertToUser(UserCreateDTO userCreateDTO) {
        return User.builder()
                .username(userCreateDTO.getUsername())
                .password(userCreateDTO.getPassword())
                .email(userCreateDTO.getEmail())
                .firstName(userCreateDTO.getFirstName())
                .lastName(userCreateDTO.getLastName())
                .loyalty(Loyalty.BRONZE)
                .discount(Discount.NO_DISCOUNT) // Set the default discount value to NO_DISCOUNT
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserCreateDTO userCreateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Update the user's fields
        existingUser.setUsername(userCreateDTO.getUsername());
        existingUser.setPassword(userCreateDTO.getPassword());
        existingUser.setEmail(userCreateDTO.getEmail());
        existingUser.setFirstName(userCreateDTO.getFirstName());
        existingUser.setLastName(userCreateDTO.getLastName());

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok().body(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}