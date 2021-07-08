package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Department;
import uz.mk.communicationcompanyservice.entity.Role;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
