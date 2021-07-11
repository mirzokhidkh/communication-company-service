package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Package;

@RepositoryRestResource(path = "package",collectionResourceRel = "list")
public interface PackageRepository extends JpaRepository<Package, Integer> {
}
