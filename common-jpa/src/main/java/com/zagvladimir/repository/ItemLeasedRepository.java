package com.zagvladimir.repository;

import com.zagvladimir.domain.ItemLeased;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemLeasedRepository extends  JpaRepository<ItemLeased,Long> {

    @Query("select r.credentials.userLogin from ItemLeased i inner join i.renter r where i.id = :id")
    String getRenterName(Long id);

    List<ItemLeased> findAllByRenterId(long id);
}
