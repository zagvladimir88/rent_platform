package com.zagvladimir.repository;

import com.zagvladimir.domain.ItemLeased;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemLeasedRepository extends CrudRepository<ItemLeased,Long>, JpaRepository<ItemLeased,Long>, PagingAndSortingRepository<ItemLeased,Long> {
}
