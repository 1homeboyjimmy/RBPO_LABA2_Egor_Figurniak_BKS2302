package example.controller;

import example.entity.Movement;
import example.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping
    public List<Movement> getAllMovements() {
        return movementService.getAllMovements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@PathVariable Long id) {
        Movement movement = movementService.getMovementById(id);
        if (movement != null) {
            return ResponseEntity.ok(movement);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody Movement movement) {
        Movement created = movementService.createMovement(movement);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movement> updateMovement(@PathVariable Long id, @RequestBody Movement updatedMovement) {
        Movement updated = movementService.updateMovement(id, updatedMovement);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return ResponseEntity.noContent().build();
    }
}