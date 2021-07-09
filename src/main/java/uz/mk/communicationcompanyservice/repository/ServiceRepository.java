package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Service;

@RepositoryRestResource(path = "service",collectionResourceRel = "list")
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
