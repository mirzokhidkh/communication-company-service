package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Simcard;

import java.util.UUID;

@Repository
public interface SimcardRepository extends JpaRepository<Simcard, UUID> {

}
