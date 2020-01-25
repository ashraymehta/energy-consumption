package com.zenhomes.energyconsumption.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class AbstractRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        this.mongoTemplate.getCollectionNames().forEach((collection) -> {
            this.mongoTemplate.remove(new Query(), collection);
        });
    }

}