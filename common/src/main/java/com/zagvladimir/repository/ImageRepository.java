package com.zagvladimir.repository;

import com.zagvladimir.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    List<Image> findImageByItemsId(Long itemId);

}
