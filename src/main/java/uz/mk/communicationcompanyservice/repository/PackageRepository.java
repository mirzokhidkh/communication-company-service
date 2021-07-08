package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {

}
