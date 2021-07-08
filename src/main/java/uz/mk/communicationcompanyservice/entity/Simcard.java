package uz.mk.communicationcompanyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Simcard {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private String companyCode;

    @Column(nullable = false)
    private String number;

    private boolean status;

    @OneToOne
    private User client;

    @ManyToOne
    private Tariff tariff;
}
