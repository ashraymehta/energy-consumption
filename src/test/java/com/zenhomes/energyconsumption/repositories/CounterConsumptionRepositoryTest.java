package com.zenhomes.energyconsumption.repositories;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@DataMongoTest(includeFilters = @ComponentScan.Filter(Repository.class))
class CounterConsumptionRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CounterConsumptionRepository counterConsumptionRepository;

    @Test
    void shouldInsertCounterConsumption() {
        final var counterConsumption = new CounterConsumption("counterId", 1_000D);

        final var savedCounterConsumption = counterConsumptionRepository.insert(counterConsumption);

        final var savedCounterConsumptionId = savedCounterConsumption.getId();
        final var countOfAllSavedCounterConsumptions = mongoTemplate.count(new Query(), CounterConsumption.class);
        final var foundCounterConsumption = mongoTemplate.findById(savedCounterConsumptionId, CounterConsumption.class);
        assertThat(countOfAllSavedCounterConsumptions, is(equalTo(1L)));
        assertThat(foundCounterConsumption, is(equalTo(counterConsumption)));
    }

    @Test
    void shouldAutomaticallyAuditCreationDate() {
        final var counterConsumption = new CounterConsumption("counterId", 1_000D);

        final var startTime = new Date();
        final var savedCounterConsumption = counterConsumptionRepository.insert(counterConsumption);
        final var endTime = new Date();

        assertThat(savedCounterConsumption.getCreatedAt(), is(greaterThanOrEqualTo(startTime)));
        assertThat(savedCounterConsumption.getCreatedAt(), is(lessThanOrEqualTo(endTime)));
    }

    @Test
    void shouldFindSumOfConsumptionForAllCountersFromTheProvidedTimePeriod() {
//       given
        counterConsumptionRepository.insert(new CounterConsumption("not-be-considered-counter", 10_000.21));

        final var startDate = new Date();

        counterConsumptionRepository.insert(new CounterConsumption("1", 500.01));
        counterConsumptionRepository.insert(new CounterConsumption("1", 499.99));
        counterConsumptionRepository.insert(new CounterConsumption("2", 129.99));

//      when
        final var counterConsumptionStatistics = counterConsumptionRepository.findCounterConsumptionStatistics(startDate);

//      then
        assertThat(counterConsumptionStatistics, hasSize(2));

        final var consumptionOfFirstCounter = counterConsumptionStatistics.get(0);
        final var consumptionOfSecondCounter = counterConsumptionStatistics.get(1);
        assertThat(consumptionOfFirstCounter.getEnergyConsumed(), is(equalTo(1_000.00)));
        assertThat(consumptionOfSecondCounter.getEnergyConsumed(), is(equalTo(129.99)));
    }
}