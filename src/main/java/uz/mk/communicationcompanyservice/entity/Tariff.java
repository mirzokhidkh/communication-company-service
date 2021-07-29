package uz.mk.communicationcompanyservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.mk.communicationcompanyservice.entity.template.AbsIntegerEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tariff extends AbsIntegerEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private Double price = 0.0;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TariffSet tariffSet;

    private Double switchCost = 0.0;

    private Date validityPeriod;

    @ManyToMany
    private List<Ussd> ussd;

    @ManyToMany
    private List<Package> packages;

    @ManyToMany
    private List<ExtraService> services;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Simcard> simcard;
}
