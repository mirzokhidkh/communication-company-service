package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.ExtraService;

import java.util.List;

@RepositoryRestResource(path = "service",collectionResourceRel = "list")
public interface InfoAndEntertainmentServiceRepository extends JpaRepository<ExtraService, Integer> {


    List<ExtraService> findAllByServiceTypeId(Integer serviceType_id);


}
