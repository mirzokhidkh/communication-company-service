package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.SimcardSet;

import java.util.UUID;

@RepositoryRestResource(path = "simcardSet", collectionResourceRel = "list")
public interface SimcardSetRepository extends JpaRepository<SimcardSet, UUID> {


}
