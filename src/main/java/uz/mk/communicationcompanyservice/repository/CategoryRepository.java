package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Category;
import uz.mk.communicationcompanyservice.entity.Service;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
