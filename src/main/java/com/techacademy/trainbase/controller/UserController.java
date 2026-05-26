package com.techacademy.trainbase.controller;

import com.techacademy.trainbase.dto.UserCreateDTO;
import com.techacademy.trainbase.dto.UserDTO;
import com.techacademy.trainbase.dto.UserUpdateDTO;
import com.techacademy.trainbase.entity.User;
import com.techacademy.trainbase.exception.ResourceNotFoundException;
import com.techacademy.trainbase.mapper.UserMapper;
import com.techacademy.trainbase.response.ApiResponse;
import com.techacademy.trainbase.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers(HttpServletRequest request) {
        List<UserDTO> users = userService.getAllUsers().stream()
            .map(userMapper::toDTO)
            .toList();
        return success(users);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersPaginated(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request) {
        Page<User> userPage = userService.getUsersPaginated(pageable);
        Page<UserDTO> dtoPage = userPage.map(userMapper::toDTO);
        return paginated(dtoPage, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(
            @PathVariable Long id,
            HttpServletRequest request) {
        User user = userService.getUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return success(userMapper.toDTO(user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(
            @Valid @RequestBody UserCreateDTO userCreateDTO,
            HttpServletRequest request) {
        User user = userMapper.toEntity(userCreateDTO);
        User createdUser = userService.createUser(user);
        return created(userMapper.toDTO(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO,
            HttpServletRequest request) {
        User existingUser = userService.getUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userMapper.updateEntityFromDTO(userUpdateDTO, existingUser);
        User updatedUser = userService.updateUser(id, existingUser);
        return updated(userMapper.toDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id,
            HttpServletRequest request) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return deleted();
        }
        return notFound("User", id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUsername(
            @PathVariable String username,
            HttpServletRequest request) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return success(userMapper.toDTO(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(
            @PathVariable String email,
            HttpServletRequest request) {
        User user = userService.getUserByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return success(userMapper.toDTO(user));
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<ApiResponse<Boolean>> existsByUsername(
            @PathVariable String username,
            HttpServletRequest request) {
        boolean exists = userService.existsByUsername(username);
        return success(exists);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> existsByEmail(
            @PathVariable String email,
            HttpServletRequest request) {
        boolean exists = userService.existsByEmail(email);
        return success(exists);
    }
}
