package task.aisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.aisa.entity.ServiceEntity;

@Repository
public interface ServiceInteractionRepository extends JpaRepository<ServiceEntity, Long> {
}
