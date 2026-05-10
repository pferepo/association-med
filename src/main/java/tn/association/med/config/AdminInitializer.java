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
    public void run(String... args) {

        // ========================= 
        // ADMIN INIT
        // =========================
        boolean adminExists = userRepository.existsByRole(Role.ADMIN);

        if (!adminExists) {
            User admin = new User();
            admin.setNom("admin");
            admin.setPrenom("admin");
            admin.setEmail("pfe2475@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456@@@@@@"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);

            userRepository.save(admin);
        }

        // =========================
        // COMPTE APPLI INIT
        // =========================
        boolean compteAppliExists =
                userRepository.findByPrenomIgnoreCase("compteApplication").isPresent();

        if (!compteAppliExists) {

            User compteAppli = new User();

            compteAppli.setNom("AMB");
            compteAppli.setPrenom("compteApplication");
            compteAppli.setEmail("setEmail@system.locale");

            // password NON UTILISABLE (sécurisé)
            compteAppli.setPassword(passwordEncoder.encode("NO_LOGIN_ACCESS"));

            compteAppli.setRole(Role.MEMBRE_INVITE);
            compteAppli.setActive(false);

            userRepository.save(compteAppli);
        }

    }
}