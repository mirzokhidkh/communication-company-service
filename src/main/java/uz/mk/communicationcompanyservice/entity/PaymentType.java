package uz.mk.communicationcompanyservice.entity;

import lombok.Data;
import uz.mk.communicationcompanyservice.entity.enums.PaymentTypeName;

import javax.persistence.*;

@Data
@Entity
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PaymentTypeName paymentTypeName;
}
