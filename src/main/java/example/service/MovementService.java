package example.service;

import example.entity.Movement;
import example.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepo;

    public List<Movement> getAllMovements() {
        return movementRepo.findAll();
    }

    public Movement getMovementById(Long id) {
        return movementRepo.findById(id).orElse(null);
    }

    public Movement createMovement(Movement movement) {
        return movementRepo.save(movement);
    }

    public Movement updateMovement(Long id, Movement updatedMovement) {
        if (movementRepo.existsById(id)) {
            updatedMovement.setId(id);
            return movementRepo.save(updatedMovement);
        }
        return null;
    }

    public void deleteMovement(Long id) {
        movementRepo.deleteById(id);
    }
}