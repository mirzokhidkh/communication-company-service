package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.BranchOffice;

@RepositoryRestResource(path = "branchOffice",collectionResourceRel = "list")
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Integer> {

}
