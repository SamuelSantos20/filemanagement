package filemanagement.dev.git.file.controllerTest;
import filemanagement.dev.git.file.controller.UserController;
import filemanagement.dev.git.file.dto.UserDto;
import filemanagement.dev.git.file.mapper.UserMapper;
import filemanagement.dev.git.file.model.User;
import filemanagement.dev.git.file.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "spring.datasource.url=${DATASOURCE_TESTE_URL}",
        "spring.datasource.username=${DATASOURCE_TESTE_USERNAME}",
        "spring.datasource.password=${DATASOURCE_TESTE_PASSWORD}",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.flyway.enabled=false"})
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserMapper userMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserid(1);
        testUser.setEmail("test@test.com");
        testUser.setUsername("Test User");
    }

    @Test
    void createUser_Success() {
        when(userService.saveUser(any(User.class))).thenReturn(testUser);
        UserDto userDto = new UserDto(testUser.getUsername(), testUser.getEmail(), testUser.getPasswordHash());
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(testUser);
        ResponseEntity<User> response = userController.createUser(userDto);
        assertNotNull(response.getBody());
        assertEquals(testUser.getUserid(), response.getBody().getUserid());
    }

    @Test
    void getUserById_Success() {
        when(userService.getUserById(1)).thenReturn(testUser);
        when(userMapper.toDTO(testUser)).thenReturn(new UserDto(testUser.getUsername(), testUser.getEmail(), testUser.getPasswordHash()));
        ResponseEntity<Object> response = userController.getUserById(1);
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof UserDto);
        UserDto responseDto = (UserDto) response.getBody();
        assertEquals(testUser.getUsername(), responseDto.username());
    }

    @Test
    void updateUser_Success() {
        when(userService.updateUser(any(Integer.class), any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(testUser)).thenReturn(new UserDto(testUser.getUsername(), testUser.getEmail(), testUser.getPasswordHash()));
        ResponseEntity<UserDto> response = userController.updateUser(1, testUser);
        assertNotNull(response.getBody());
        assertEquals(testUser.getUsername(), response.getBody().username());
    }

    @Test
    void deleteUser_Success() {
        userController.deleteUser(1);
    }
}
