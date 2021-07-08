package uz.mk.communicationcompanyservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    private Double price;

    private Double switchCost;

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

}
