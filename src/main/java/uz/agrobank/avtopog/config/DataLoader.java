package uz.agrobank.avtopog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.model.enums.RoleEnum;
import uz.agrobank.avtopog.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    private final MyPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {

        if (ddl!=null && ddl.contains("create")){

            User admin=new User(1L,passwordEncoder.passwordEncoder().encode("admin"),"Anvar","admin","+998997777777",true, RoleEnum.ADMIN);
            User user=new User(2L,passwordEncoder.passwordEncoder().encode("user"),"Aziz","user","+998997777779",true, RoleEnum.USER);
             userRepository.save(admin);
             userRepository.save(user);

        }

    }
}
