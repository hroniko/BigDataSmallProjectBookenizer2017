package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersEntityDao extends CrudRepository<UsersEntity, Long> {

}
