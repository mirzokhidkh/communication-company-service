package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.InfoAndEntertainmentService;

import java.util.List;

@RepositoryRestResource(path = "service",collectionResourceRel = "list")
public interface InfoAndEntertainmentServiceRepository extends JpaRepository<InfoAndEntertainmentService, Integer> {


    List<InfoAndEntertainmentService> findAllByServiceTypeId(Integer serviceType_id);


}
