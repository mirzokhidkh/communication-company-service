package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.BranchOffice;

@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Integer> {

}
