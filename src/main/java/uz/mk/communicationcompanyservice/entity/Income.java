package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uz.mk.communicationcompanyservice.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income extends AbsUUIDEntity {
    @Column(nullable = false)
    private Double amount = 0.0;

    @ManyToOne
    private Simcard simcard;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp date;

    @ManyToOne
    private ClientMoveType clientMoveType;

    @ManyToOne
    private PaymentType paymentType;

}
