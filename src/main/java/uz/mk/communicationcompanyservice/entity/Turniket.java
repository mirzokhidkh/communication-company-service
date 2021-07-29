package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.mk.communicationcompanyservice.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Turniket extends AbsUUIDEntity {
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    private User staff;

    private boolean status;
}
