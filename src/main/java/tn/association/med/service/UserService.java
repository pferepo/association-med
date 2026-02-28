package tn.association.med.service;

import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    void deleteUser(Long id);
}
