package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDto;
import com.example.demo.exception.UserEmailAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing user operations.
 *
 * This class provides methods to create new users, find users by email, retrieve
 * the leaderboard of top users, and get a list of all users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user based on the provided {@code UserDto}.
     *
     * @param userDto The data transfer object containing user details.
     * @return The newly created {@code User} object.
     * @throws UserEmailAlreadyExistsException if a user with the same email already exists.
     */
    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserEmailAlreadyExistsException("User email " + userDto.getEmail() + " already exists.");
        }
        User user = new User(userDto.getName(), userDto.getEmail());
        return userRepository.save(user);
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The {@code User} object with the specified email.
     * @throws UserNotFoundException if no user with the given email is found.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User email not found: " + email));
    }

    /**
     * Retrieves the leaderboard of top users, sorted by the number of wins.
     *
     * @return A list of the top users, limited to a maximum of 10 users.
     */
    public List<User> getLeaderboard() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "wins"))
                .subList(0, Math.min(10, (int) userRepository.count()));
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all {@code User} objects.
     */
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
