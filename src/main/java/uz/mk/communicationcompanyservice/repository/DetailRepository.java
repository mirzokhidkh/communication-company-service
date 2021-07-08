package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Detail;

import java.util.UUID;

@Repository
public interface DetailRepository extends JpaRepository<Detail, UUID> {

}
