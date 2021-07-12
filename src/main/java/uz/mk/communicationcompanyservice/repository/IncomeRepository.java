package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.Income;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RepositoryRestResource(path = "income", collectionResourceRel = "list")
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    List<Income> findAllByDateBetween(Timestamp minDate, Timestamp maxDate);
}
