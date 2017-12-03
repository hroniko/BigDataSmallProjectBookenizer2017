package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.entity.AuthorsEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorsEntityDao extends CrudRepository<AuthorsEntity, Long> {
}
