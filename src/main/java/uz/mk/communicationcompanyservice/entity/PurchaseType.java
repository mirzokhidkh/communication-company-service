package uz.mk.communicationcompanyservice.entity;

import lombok.Data;
import uz.mk.communicationcompanyservice.entity.enums.PurchaseTypeName;

import javax.persistence.*;

@Data
@Entity
public class PurchaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PurchaseTypeName purchaseTypeName;

    private Integer durationDays;
}
