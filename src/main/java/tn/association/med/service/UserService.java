package tn.association.med.service;

import org.springframework.web.multipart.MultipartFile;
import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;
import tn.association.med.entities.User;

import java.util.List;

public interface UserService {



    UserResponseDTO createUser(UserRequestDTO dto);

    User getUserEntityById(Long id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    User getUserByEmail(String email);

    List<UserResponseDTO> getListeMembres();

    UserResponseDTO toggleUserActive(Long id);

    void deleteUser(Long id);

    public void sendResetCode(String email);

    public void resetPassword(String email, String code, String newPassword);


    List<String> getAllEmailUsers();

    UserResponseDTO updateUser(Long id, UserRequestDTO dto);

    org.springframework.data.domain.Page<UserResponseDTO> getUsers(org.springframework.data.domain.Pageable pageable);

    List<UserResponseDTO> searchUsers(String keyword);

    User uploadUserImage(Long id, MultipartFile file) throws Exception;

    long countActiveUsers();

    long countInactiveUsers();

    boolean existsByEmail(String email);
}
