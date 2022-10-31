package com.zagvladimir.repository;

import com.zagvladimir.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GradeRepository extends JpaRepository<Grade,Long> {

    @Query("select r.credentials.userLogin from Grade g inner join g.user r where g.id = :id")
    String getLoginWhoRatedByGradeId(Long id);
}
