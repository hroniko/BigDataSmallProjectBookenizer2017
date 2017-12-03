package com.bigdata.bookenizer.services;

import com.bigdata.bookenizer.model.entity.RecomendationsEntity;
import org.springframework.data.repository.CrudRepository;

public interface RecomendationsEntityDao extends CrudRepository<RecomendationsEntity, Long> {
}
