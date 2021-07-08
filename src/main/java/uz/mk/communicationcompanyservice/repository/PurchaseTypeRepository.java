package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.PurchaseType;

@Repository
public interface PurchaseTypeRepository extends JpaRepository<PurchaseType, Integer> {

}
