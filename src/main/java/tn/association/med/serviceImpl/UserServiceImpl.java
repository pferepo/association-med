package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;
import tn.association.med.entities.PasswordResetCode;
import tn.association.med.entities.User;
import tn.association.med.enums.Genre;
import tn.association.med.enums.Role;
import tn.association.med.mapper.UserMapper;
import tn.association.med.repository.PasswordResetCodeRepository;
import tn.association.med.repository.UserRepository;
import tn.association.med.service.UserService;
import tn.association.med.serviceImpl.images.FileService;
import tn.association.med.serviceImpl.notification.EmailNotifsService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotifsService emailNotifsService;
    private final PasswordResetCodeRepository resetRepo;


    private final FileService fileService;

    @Override
    public User uploadUserImage(Long id, MultipartFile file) throws Exception {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File vide");
        }

        // SUPPRIMER ANCIENNE IMAGE
        if (user.getImageUrl() != null) {
            fileService.deleteImage(user.getImageUrl());
        }

        // SAUVEGARDER NOUVELLE IMAGE
        String url = fileService.saveImage(file);

        user.setImageUrl(url);

        return userRepository.save(user);
    }

    @Override
    public long countActiveUsers() {
        return 0;
    }

    @Override
    public long countInactiveUsers() {
        return 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public List<User> findByRole(String role) {
    	// donne les utilisateurs qui ont ce role (dans notre cas : MEMBRE_BUREAU_EXECUTIF )
        return userRepository.findByRole(Role.valueOf(role));
    }

    @Override
    public User getCompteAppli() {
        return userRepository.findByPrenomIgnoreCase("compteApplication")
                .orElseThrow(() -> new RuntimeException("Utilisateur compteApplication introuvable"));
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {

        User user = userMapper.toEntity(dto);

        // cryptage du mot de passe
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (user.getActive() == null) {
            user.setActive(false);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
    public UserResponseDTO toggleUserActive(Long id) {

        // Récupérer l'utilisateur
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User introuvable"));

        // Inverser le statut actif
        user.setActive(!user.getActive());

        // Sauvegarder l'utilisateur
        User saved = userRepository.save(user);

        // Envoyer un email de notification
        // Titre et message personnalisés selon le nouveau statut
        String titre = saved.getActive() ? "Votre compte est activé." : "Votre compte est désactivé.";
        String description = saved.getActive()
                ? "Bonjour " + saved.getPrenom() + ", votre compte a été activé. Vous pouvez désormais vous connecter."
                : "Bonjour " + saved.getPrenom() + ", votre compte a été désactivé. Vous ne pourrez plus vous connecter.";

        emailNotifsService.envoyerEmail(saved.getEmail(), titre, description);

        // Retourner l'utilisateur mis à jour
        return userMapper.toDto(saved);
    }


    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDto(user);
    }

    @Override
    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user;
    }

   
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // SUPPRIMER IMAGE SI EXISTE
        if (user.getImageUrl() != null) {
            fileService.deleteImage(user.getImageUrl());
        }

        // SUPPRIMER USER
        userRepository.delete(user);
    }

    @Override
    public void sendResetCode(String email) {

        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 🔥 SUPPRIMER anciens codes
        resetRepo.deleteAllByEmail(email);

        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        PasswordResetCode reset = new PasswordResetCode();
        reset.setEmail(email);
        reset.setCode(code);
        reset.setExpiration(LocalDateTime.now().plusMinutes(10));
        reset.setUsed(false);

        resetRepo.save(reset);

        emailNotifsService.envoyerEmail(
                email,
                "Code de réinitialisation",
                "Votre code est : " + code
        );
    }
    @Override
    public void resetPassword(String email, String code, String newPassword) {

        PasswordResetCode reset = resetRepo
                .findTopByEmailAndCodeOrderByExpirationDesc(email, code)
                .orElseThrow(() -> new RuntimeException("Code invalide"));

        if (reset.isUsed()) {
            throw new RuntimeException("Code déjà utilisé");
        }

        if (reset.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code expiré");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        reset.setUsed(true);
        resetRepo.save(reset);
    }

    @Override
    public List<String> getAllEmailUsers() {
        return userRepository.findAllMail();
    }

    @Override
    public List<String> getBureauEmails() {
        return userRepository.findBureauEmails();
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setTel(dto.getTel());
        user.setGrade(dto.getGrade());
        user.setCin(dto.getCin());

        if (dto.getGenre() != null) {
            user.setGenre(Genre.valueOf(dto.getGenre()));
        }

        user.setRole(dto.getRole());

        User saved = userRepository.save(user);

        return userMapper.toDto(saved);
    }

    @Override
    public org.springframework.data.domain.Page<UserResponseDTO> getUsers(org.springframework.data.domain.Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    public java.util.List<UserResponseDTO> searchUsers(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll()
                    .stream()
                    .map(userMapper::toDto)
                    .toList();
        }

        return userRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}