package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.appwarehouse.entity.Output;

import java.util.Date;
import java.util.List;

@Repository
public interface OutputRepository extends JpaRepository<Output,Integer> {
    @Query(value = "select * from output where date BETWEEN :startDate AND :endDate",nativeQuery = true)
    List<Output> getOutputIdBetweenDates(@Param("startDate") Date startDate, @Param("endDate")Date endDate);
}
