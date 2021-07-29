package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.mk.communicationcompanyservice.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Detail extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(updatable = false,nullable = false)
    @CreationTimestamp
    private Timestamp date;

    @ManyToOne
    private ClientMoveType clientMoveType;

    @ManyToOne
    private Simcard simcard;
}
