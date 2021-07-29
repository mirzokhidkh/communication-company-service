package uz.mk.communicationcompanyservice.entity;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.mk.communicationcompanyservice.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Simcard extends AbsUUIDEntity implements UserDetails {
    private String countryCode = "+998";

    @Column(nullable = false)
    private String companyCode;

    @Column(nullable = false)
    private String number;

    @Column(unique = true)
    private String simCardBackNumber;

    private Double balance = 0.0;

    private String pinCode;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SimcardSet simcardSet;

    private boolean status;

    @ManyToMany
    private List<Package> currentPackage;

    @ManyToMany
    private List<ExtraService> currentService;

    @ManyToOne
    private User client;

    @ManyToOne
    private Tariff tariff;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return this.pinCode;
    }

    @Override
    public String getUsername() {
        return this.simCardBackNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
