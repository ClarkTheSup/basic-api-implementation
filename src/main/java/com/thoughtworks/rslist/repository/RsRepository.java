package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RsRepository extends CrudRepository<RsDto, Integer> {
    @Override
    List<RsDto> findAll();
}
