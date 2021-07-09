package uz.mk.communicationcompanyservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    private Double price = 0.0 ;

    private Double switchCost = 0.0;

    private Date validityPeriod;

    @ManyToMany
    private List<Ussd> ussd;

    @ManyToMany
    private List<Package> packages;

    @ManyToMany
    private List<Service> services;


    @JsonIgnore
    @OneToMany(mappedBy = "tariff",cascade = CascadeType.ALL)
    private List<Simcard> simcard;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @Column(updatable = false,nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
