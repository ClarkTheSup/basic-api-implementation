package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RsRepository extends CrudRepository<RsDto, Integer> {
    @Override
    List<RsDto> findAll();

    @Modifying
    @Query(value = "update rsevent r set r.name= ?, r.keyword= ? " +
            "where r.id = ?", nativeQuery = true)
    @Transactional
    void updateRsById(String name, String keyword, int id);
}
