package com.haptm.chatapp.service;

import com.haptm.chatapp.exception.InvalidPasswordException;
import com.haptm.chatapp.exception.UserExistException;
import com.haptm.chatapp.exception.UserNotFoundException;
import com.haptm.chatapp.model.User;
import com.haptm.chatapp.reposiroty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User with username: " + username + " not found");
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User with email: " + email + " not found");
        }
    }

    public Set<User> getAllFriends(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getFriends();
        }
        else {
            throw new UserNotFoundException("User with id: " + userId + " not found");
        }
    }

    private void checkIfUserExistsByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new UserExistException("User with username: " + username + " already exists");
        }
    }

    private void checkIfUserExistsByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserExistException("User with email: " + email + " already exists");
        }
    }

    public User createUser(User user) {
        checkIfUserExistsByUsername(user.getUsername());
        checkIfUserExistsByEmail(user.getEmail());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        checkIfUserExistsByUsername(user.getUsername());
        checkIfUserExistsByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    public User updatePassword(Long id, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            if(userToUpdate.getPasswordHash().equals(oldPassword)) {
                userToUpdate.setPasswordHash(newPassword);
                return userRepository.save(userToUpdate);
            }
            else {
                throw new InvalidPasswordException("Invalid password");
            }
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    public User addFriend(Long userId, Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " not found");
        }
        Optional<User> friendOptional = userRepository.findById(friendId);
        if (friendOptional.isEmpty()) {
            throw new UserNotFoundException("User with id: " + friendId + " not found");
        }
        User user = userOptional.get();
        User friend = friendOptional.get();
        user.getFriends().add(friend);
        return userRepository.save(user);
    }

    public User deleteFriend(Long userId, Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " not found");
        }
        Optional<User> friendOptional = userRepository.findById(friendId);
        if (friendOptional.isEmpty()) {
            throw new UserNotFoundException("User with id: " + friendId + " not found");
        }
        User user = userOptional.get();
        User friend = friendOptional.get();
        user.getFriends().remove(friend);
        return userRepository.save(user);
    }

    public void deleteUser(Long id, String password) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getPasswordHash().equals(password)) {
                userRepository.delete(user);
            }
            else {
                throw new InvalidPasswordException("Invalid password");
            }
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }
}
