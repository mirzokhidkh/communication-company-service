package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.PurchasedItemType;
import uz.mk.communicationcompanyservice.entity.enums.PurchasedItemTypeName;

@Repository
public interface PurchasedItemTypeRepository extends JpaRepository<PurchasedItemType, Integer> {
    PurchasedItemType findByName(PurchasedItemTypeName name);
}
