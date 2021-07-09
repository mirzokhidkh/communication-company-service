package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Ussd;

@RepositoryRestResource(path = "ussd",collectionResourceRel = "list")
public interface UssdRepository extends JpaRepository<Ussd, Integer> {

}
