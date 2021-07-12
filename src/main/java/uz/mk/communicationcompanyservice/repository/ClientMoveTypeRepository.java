package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.ClientMoveType;
import uz.mk.communicationcompanyservice.entity.Role;
import uz.mk.communicationcompanyservice.entity.enums.ClientMoveTypeName;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;

import java.util.Collection;
import java.util.Set;

@Repository
public interface ClientMoveTypeRepository extends JpaRepository<ClientMoveType, Integer> {
    ClientMoveType findByName(ClientMoveTypeName name);
}
