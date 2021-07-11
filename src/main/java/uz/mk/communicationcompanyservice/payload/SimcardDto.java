package uz.mk.communicationcompanyservice.payload;

import lombok.Data;
import uz.mk.communicationcompanyservice.entity.Tariff;
import uz.mk.communicationcompanyservice.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SimcardDto {
    @NotNull
    private String companyCode;

    @NotNull
    private String number;

    private Double balance = 0.0;

    private boolean status = true;

    @NotNull
    private UUID clientId;

    @NotNull
    private Integer tariffId;

    private Integer paymentTypeId;

    private Double price;
}
