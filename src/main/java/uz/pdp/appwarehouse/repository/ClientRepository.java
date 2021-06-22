package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.entity.Supplier;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    boolean existsByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
}
