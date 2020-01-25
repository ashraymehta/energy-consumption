package com.zenhomes.energyconsumption.repositories;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CounterConsumptionRepository {
    private final MongoTemplate mongoTemplate;

    public CounterConsumptionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public CounterConsumption insert(CounterConsumption counterConsumption) {
        return this.mongoTemplate.insert(counterConsumption);
    }
}
