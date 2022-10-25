package com.zagvladimir.service.sub_item_type;

import com.zagvladimir.domain.SubItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubItemTypeService {

    Page<SubItemType> findAll(Pageable pageable);

    List<SubItemType> findAll();

    SubItemType create(SubItemType subItemType, Long itemCategoryId);

    Optional<SubItemType> findById(Long subItemTypeId);

    SubItemType update(SubItemType subItemType);

    Long delete(Long subItemTypeId);

}
