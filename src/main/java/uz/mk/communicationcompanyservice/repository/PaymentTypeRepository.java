package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.PaymentType;
import uz.mk.communicationcompanyservice.entity.enums.PaymentTypeName;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
    PaymentType findByPaymentTypeName(PaymentTypeName paymentTypeName);
}
