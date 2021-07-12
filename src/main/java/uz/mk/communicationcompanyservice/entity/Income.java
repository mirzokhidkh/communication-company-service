package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income {
    @Id
    @GeneratedValue
    private UUID id;

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
