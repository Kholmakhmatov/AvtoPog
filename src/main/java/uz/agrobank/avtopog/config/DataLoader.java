package uz.agrobank.avtopog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.model.enums.RoleEnum;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value(value = "${dataloader.status}")
    private String ddl;

    private final MyPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        if (ddl != null && ddl.contains("create")) {
            User byId = userRepository.findById(1L);
            if (byId==null) {
                User admin = new User(1L, passwordEncoder.passwordEncoder().encode("admin"), "Anvar", "admin", "+998", true, RoleEnum.ADMIN);
                userRepository.save(admin);
            }

        }

    }
}
