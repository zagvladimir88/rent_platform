package com.zagvladimir.repository;

import com.zagvladimir.domain.ItemLeased;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLeasedRepository extends  JpaRepository<ItemLeased,Long> {
}
