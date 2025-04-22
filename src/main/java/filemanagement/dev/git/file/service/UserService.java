package filemanagement.dev.git.file.service;

import filemanagement.dev.git.file.model.User;
import filemanagement.dev.git.file.repository.UserRepository;
import filemanagement.dev.git.file.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class UserService {

    private final UserRepository userRepository;

    private final UserValidation userValidation;

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public User saveUser(User user) {
        userValidation.validation(user);

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id, User updatedUser) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPasswordHash(updatedUser.getPasswordHash());
            existingUser.setUploadLimit(updatedUser.getUploadLimit());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }


}
