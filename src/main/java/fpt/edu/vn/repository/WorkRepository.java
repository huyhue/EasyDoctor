package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Packages;

import java.util.List;

public interface WorkRepository extends JpaRepository<Packages, Integer> {
    @Query("select w from Packages w inner join w.providers p where p.id in :providerId")
    List<Packages> findByProviderId(@Param("providerId") int providerId);

    @Query("select w from Packages w where w.targetCustomer = :target ")
    List<Packages> findByTargetCustomer(@Param("target") String targetCustomer);

    @Query("select w from Packages w inner join w.providers p where p.id in :providerId and w.targetCustomer = :target ")
    List<Packages> findByTargetCustomerAndProviderId(@Param("target") String targetCustomer, @Param("providerId") int providerId);
}
