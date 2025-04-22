package filemanagement.dev.git.file.validation;

import filemanagement.dev.git.file.exception.DuplicateRecord;
import filemanagement.dev.git.file.model.User;

import filemanagement.dev.git.file.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;


    public void validation(User user) {

        if (exists(user)){

            throw new DuplicateRecord("Já existe uma conta criada para esse usuario!");

        }


    }


    private boolean exists(User user){

        Optional<User> byUserId = userRepository.findByEmail(user.getEmail());

        if (user.getUserid() == null){

            return byUserId.isPresent();
        }

        return byUserId.stream().anyMatch(user1 -> !user1.equals(user.getEmail()));
    }


}
