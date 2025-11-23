package example.controller;

import example.entity.User;
import example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')") // Только ADMIN может обращаться ко всем методам по умолчанию
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // Получить всех пользователей (только ADMIN)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

    // Получить текущего авторизованного пользователя (любой USER или ADMIN)
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // USER и ADMIN могут получить себя
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    // Обновить роль пользователя по ID (только ADMIN)
    @PutMapping("/{id}/role")
    public ResponseEntity<String> updateRole(@PathVariable Long id, @RequestParam String role) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (!"USER".equals(role) && !"ADMIN".equals(role)) {
            return ResponseEntity.badRequest().body("Недопустимая роль. Допустимые значения: USER, ADMIN");
        }
        user.setRole(role);
        userRepo.save(user);
        return ResponseEntity.ok("Роль обновлена");
    }
}