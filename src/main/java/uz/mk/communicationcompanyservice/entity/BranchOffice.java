package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.mk.communicationcompanyservice.entity.template.AbsIntegerEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BranchOffice extends AbsIntegerEntity {
    @OneToOne
    private User director;

    @OneToOne
    private Region region;

    @OneToMany
    private List<User> staff;
}
