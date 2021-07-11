package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Simcard {
    @Id
    @GeneratedValue
    private UUID id;

//    @Column(nullable = false)
    private String countryCode="+998";

    @Column(nullable = false)
    private String companyCode;

    @Column(nullable = false)
    private String number;

    private Double balance=0.0;

    private boolean status;

    @ManyToMany
    private List<Package> currentPackage;

    @ManyToMany
    private List<ExtraService> currentService;

    @ManyToOne
    private User client;

    @ManyToOne
    private Tariff tariff;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
