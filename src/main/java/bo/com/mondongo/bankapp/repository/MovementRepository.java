package bo.com.mondongo.bankapp.repository;

import bo.com.mondongo.bankapp.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface MovementRepository extends JpaRepository<Movement, Serializable> {
}
