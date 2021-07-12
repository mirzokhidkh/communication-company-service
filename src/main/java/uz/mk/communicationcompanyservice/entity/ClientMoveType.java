package uz.mk.communicationcompanyservice.entity;

import lombok.Data;
import uz.mk.communicationcompanyservice.entity.enums.ClientMoveTypeName;

import javax.persistence.*;

@Data
@Entity
public class ClientMoveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ClientMoveTypeName name;
}
