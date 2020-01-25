package com.zenhomes.energyconsumption.repositories;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@DataMongoTest
class CounterConsumptionRepositoryTest {

    @Autowired
    private CounterConsumptionRepository counterConsumptionRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        this.mongoTemplate.getCollectionNames().forEach((collection) -> {
            this.mongoTemplate.remove(new Query(), collection);
        });
    }

    @Test
    void shouldInsertCounterConsumption() {
        final var counterConsumption = new CounterConsumption("counterId", 1000);

        final var savedCounterConsumption = counterConsumptionRepository.insert(counterConsumption);

        final var savedCounterConsumptionId = savedCounterConsumption.getId();
        assertThat(counterConsumptionRepository.count(), is(equalTo(1L)));
        assertThat(counterConsumptionRepository.findById(savedCounterConsumptionId).orElseThrow(),
                is(equalTo(counterConsumption)));
    }
}