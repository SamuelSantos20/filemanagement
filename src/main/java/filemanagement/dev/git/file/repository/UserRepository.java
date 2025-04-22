package filemanagement.dev.git.file.repository;

import filemanagement.dev.git.file.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUsernameContainingIgnoreCase(String username);

    Optional<User> findByEmail(String email);
}
