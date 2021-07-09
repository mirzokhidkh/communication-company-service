package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Role;
import uz.mk.communicationcompanyservice.entity.Tariff;

@RepositoryRestResource(path = "tariff",collectionResourceRel = "list")
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

}
