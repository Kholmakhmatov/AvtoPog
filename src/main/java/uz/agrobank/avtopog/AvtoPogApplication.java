package uz.agrobank.avtopog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;
import java.util.Scanner;

@SpringBootApplication
public class AvtoPogApplication {

    public static void main(String[] args) {
        boolean start = isStart();
        if (start) {
            SpringApplication.run(AvtoPogApplication.class, args);
        } else {
            System.out.println("Dastur quladi sababi ma'lumotlar o'chib ketishi mumkin");
        }
    }
    @Bean
    public static boolean isStart() {
        Scanner scanner = new Scanner(System.in);
        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("/application.properties").getInputStream());
            if (props.getProperty("spring.jpa.hibernate.ddl-auto").equals("none")) {
                return true;
            } else {
                //  String confirm = JOptionPane.showInputDialog("Ma'lumotlarni o'chirib yuborma yoki o'zgartirma! Keyin bilmay qoldim dema !!!");
                System.out.println("\"Ma'lumotlarni o'chirib yuborma yoki o'zgartirma! Keyin bilmay qoldim dema !!!\"");
                System.out.print("Parol: ");
                String confirm = scanner.nextLine();
                if (confirm != null && confirm.equals("Jokha1675")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
