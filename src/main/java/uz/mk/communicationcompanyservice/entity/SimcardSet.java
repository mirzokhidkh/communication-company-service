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
import uz.mk.communicationcompanyservice.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SimcardSet extends AbsUUIDEntity {
    private Double mb = 0.0;
    private Integer sms = 0;
    private Integer minute = 0;

    @JsonIgnore
    @OneToOne(mappedBy = "simcardSet", cascade = CascadeType.ALL)
    private Simcard simcard;
}


