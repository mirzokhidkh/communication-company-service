package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.TariffSet;

import java.util.UUID;

@RepositoryRestResource(path = "tariffSet", collectionResourceRel = "list")
public interface TariffSetRepository extends JpaRepository<TariffSet, Integer> {

}
