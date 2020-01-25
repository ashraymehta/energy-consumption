package com.zenhomes.energyconsumption.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class BaseModel {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    @CreatedDate
    @Field(DatabaseFields.CREATED_AT)
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public interface DatabaseFields {
        String CREATED_AT = "createdAt";
    }
}