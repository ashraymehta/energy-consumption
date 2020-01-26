package com.zenhomes.energyconsumption.repositories;

import com.zenhomes.energyconsumption.models.BaseDocument;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.CounterConsumptionStatistics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class CounterConsumptionRepository {
    private final MongoTemplate mongoTemplate;

    public CounterConsumptionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public CounterConsumption insert(CounterConsumption counterConsumption) {
        return this.mongoTemplate.insert(counterConsumption);
    }

    public List<CounterConsumptionStatistics> findCounterConsumptionStatistics(Date from) {
        final var filterCriteria = where(BaseDocument.DatabaseFields.CREATED_AT).gte(from);
        final var aggregation = newAggregation(match(filterCriteria), group(CounterConsumption.DatabaseFields.COUNTER_ID)
                .sum(CounterConsumption.DatabaseFields.AMOUNT).as(CounterConsumptionStatistics.ClassFields.ENERGY_CONSUMED)
                .first(CounterConsumption.DatabaseFields.COUNTER_ID).as(CounterConsumptionStatistics.ClassFields.COUNTER_ID));

        return this.mongoTemplate.aggregate(aggregation, CounterConsumption.class, CounterConsumptionStatistics.class)
                .getMappedResults();
    }
}
