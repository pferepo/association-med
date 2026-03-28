package tn.association.med.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.association.med.enums.Role;
import tn.association.med.entities.User;
import tn.association.med.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si un admin existe
        boolean adminExists = userRepository.existsByRole(Role.ADMIN);

        if (!adminExists) {
            // Créer un admin par défaut
            User admin = new User();
            admin.setNom("admin");
            admin.setPrenom("admin");
            admin.setEmail("pfe2475@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456@@@@@@")); // mot de passe sécurisé
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        } else {
            System.out.println("Admin existant trouvé, pas de création nécessaire.");
        }
    }
}