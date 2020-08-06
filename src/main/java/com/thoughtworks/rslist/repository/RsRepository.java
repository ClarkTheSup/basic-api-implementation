package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RsRepository extends CrudRepository<RsDto, Integer> {
    @Override
    List<RsDto> findAll();

}
