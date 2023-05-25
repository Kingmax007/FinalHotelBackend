package classwork.demo.repositories;


import classwork.demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByUsernameEquals(String username);
    List<User> findAllByFirstNameAndLastNameEqualsIgnoreCase(String firstname,String lastname);
}