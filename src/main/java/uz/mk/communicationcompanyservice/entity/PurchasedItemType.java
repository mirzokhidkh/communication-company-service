package uz.mk.communicationcompanyservice.entity;

import lombok.Data;
import uz.mk.communicationcompanyservice.entity.enums.PurchasedItemTypeName;

import javax.persistence.*;

@Data
@Entity
public class PurchasedItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PurchasedItemTypeName name;
}
