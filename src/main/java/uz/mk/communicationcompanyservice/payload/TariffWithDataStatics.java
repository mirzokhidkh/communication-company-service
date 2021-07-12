package uz.mk.communicationcompanyservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffWithDataStatics {
    private Integer tariffId;

    private String name;

    private String description;

    private Double price = 0.0;

    private Integer tariffSetId;

    private Double switchCost = 0.0;

    private Date validityPeriod;

    private Long numberOfUses;
}
