package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appwarehouse.entity.InputProduct;

import java.util.Date;
import java.util.List;

@Repository
public interface InputProductRepository extends JpaRepository<InputProduct,Integer> {

    List<InputProduct> findAllByInputId(Integer input_id);

    @Query(value = "select * from input_product where expire_date <= :date;",nativeQuery = true)
    List<InputProduct> findAllExpiring(Date date);
}
