package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.mk.communicationcompanyservice.entity.template.AbsIntegerEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "service")
public class ExtraService extends AbsIntegerEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    private Double price = 0.0;

    @ManyToOne
    private Category category;

    @ManyToOne
    private ServiceType serviceType;

    @ManyToMany
    private List<PurchaseType> purchaseTypes;

}
