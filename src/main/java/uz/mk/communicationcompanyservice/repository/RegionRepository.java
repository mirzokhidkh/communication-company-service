package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

}
