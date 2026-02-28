package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;
import tn.association.med.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponseDTO> getAll() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}