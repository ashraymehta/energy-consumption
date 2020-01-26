package com.zenhomes.energyconsumption.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public abstract class BaseDocument extends BaseModel {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    @CreatedDate
    @Field(DatabaseFields.CREATED_AT)
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public interface DatabaseFields {
        String CREATED_AT = "createdAt";
    }
}