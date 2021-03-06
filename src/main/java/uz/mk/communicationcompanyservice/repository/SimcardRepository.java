package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.mk.communicationcompanyservice.entity.Simcard;
import uz.mk.communicationcompanyservice.entity.User;
import uz.mk.communicationcompanyservice.payload.ServiceWithDataStatics;
import uz.mk.communicationcompanyservice.payload.TariffWithDataStatics;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "simcard", collectionResourceRel = "list")
public interface SimcardRepository extends JpaRepository<Simcard, UUID> {
    Optional<Simcard> findBySimCardBackNumber(String simCardBackNumber);

    Optional<Simcard> findByClient(User client);

    Optional<Simcard> findByClientId(UUID client_id);

    Simcard getByClientId(UUID client_id);

    @Query(value = "insert into simcard_current_package(simcard_id, current_package_id) " +
            "VALUES (?1,?2)", nativeQuery = true)
    void insertPackage(Integer packageId, UUID simcardId);

//    @Query(value = "select count(*) , current_service_id from simcard_current_service group by current_service_id", nativeQuery = true)
//    List<Object> findAllBuyingServicesStatics();

    @Query(value = "select new uz.mk.communicationcompanyservice.payload.ServiceWithDataStatics(" +
            "s.id," +
            "s.name," +
            "s.value," +
            "s.description," +
            "s.price," +
            "s.category.id," +
            "s.serviceType.id, " +
            "count(i)) " +
            "from Simcard i join i.currentService s group by s.id  ")
    List<ServiceWithDataStatics> findAllBuyingServicesStatics();

    @Query(value = "select new uz.mk.communicationcompanyservice.payload.TariffWithDataStatics(" +
            "t.id,\n" +
            "t.name,\n" +
            "t.description,\n" +
            "t.price,\n" +
            "t.tariffSet.id,\n" +
            "t.switchCost,\n" +
            "t.validityPeriod,\n" +
            "count(i)) \n" +
            "from Simcard i \n" +
            "         join Tariff t on i.tariff.id = t.id \n" +
            "group by t.id")
    List<TariffWithDataStatics> findAllBuyingTariffs();

    @Query(value = "select balance from simcard where id:?1", nativeQuery = true)
    Double getBalance(UUID simCardId);

}
