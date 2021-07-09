package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Simcard;

import java.util.UUID;

@RepositoryRestResource(path = "simcard",collectionResourceRel = "list")
public interface SimcardRepository extends JpaRepository<Simcard, UUID> {

}