package filemanagement.dev.git.file.mapper;

import filemanagement.dev.git.file.dto.UserDto;
import filemanagement.dev.git.file.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-22T15:44:03-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.username() );
        user.setEmail( dto.email() );
        user.setPasswordHash( dto.passwordHash() );

        return user;
    }

    @Override
    public UserDto toDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        String username = null;
        String email = null;
        String passwordHash = null;

        username = entity.getUsername();
        email = entity.getEmail();
        passwordHash = entity.getPasswordHash();

        UserDto userDto = new UserDto( username, email, passwordHash );

        return userDto;
    }

    @Override
    public void updateEntityFromDTO(UserDto dto, User entity) {
        if ( dto == null ) {
            return;
        }

        entity.setUsername( dto.username() );
        entity.setEmail( dto.email() );
        entity.setPasswordHash( dto.passwordHash() );
    }
}
