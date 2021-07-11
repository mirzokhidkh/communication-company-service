package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.Category;

@RepositoryRestResource(path = "category",collectionResourceRel = "list")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
