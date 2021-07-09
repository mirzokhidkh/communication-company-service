package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.Region;

@RepositoryRestResource(path = "region",collectionResourceRel = "list")
public interface RegionRepository extends JpaRepository<Region, Integer> {

}
