package filemanagement.dev.git.file.controller;


import filemanagement.dev.git.file.dto.UserDto;
import filemanagement.dev.git.file.mapper.UserMapper;
import filemanagement.dev.git.file.model.User;
import filemanagement.dev.git.file.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private static final int USER_LIMIT = 4;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Validated UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setUploadLimit(USER_LIMIT);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        UserDto dto = userMapper.toDTO(userService.getUserById(id));
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> dtos = userService.getAllUsers().stream().map(userMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setUploadLimit(USER_LIMIT);
        UserDto dto = userMapper.toDTO(userService.updateUser(id, user));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
