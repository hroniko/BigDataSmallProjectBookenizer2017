package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.entity.UsertagsEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsertagsEntityDao extends CrudRepository<UsertagsEntity, Long> {
}
