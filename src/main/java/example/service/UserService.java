package example.service;

import example.entity.User;
import example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return false; // пользователь уже существует
        }

        // Проверка надёжности пароля
        if (!isPasswordStrong(user.getPassword())) {
            return false; // пароль не надёжный
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // по умолчанию
        userRepo.save(user);
        return true;
    }

    private boolean isPasswordStrong(String password) {
        // Проверка: длина >= 8, содержит цифры, заглавные, строчные, спецсимволы
        return password.length() >= 8 &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[!@#$%^&*()].*");
    }
}