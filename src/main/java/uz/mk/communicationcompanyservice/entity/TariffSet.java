package uz.mk.communicationcompanyservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.mk.communicationcompanyservice.entity.template.AbsIntegerEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TariffSet extends AbsIntegerEntity {
    private Double mb;

    private Integer sms;

    private Integer minute;

    private Double internetTrafficPrice;

    private Double smsPrice;

    private Double outgoingCallPrice;

    @JsonIgnore
    @OneToOne(mappedBy = "tariffSet", cascade = CascadeType.ALL)
    private Tariff tariff;
}


