package com.org.cache.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Aircraft {

    @Id
    private String id;

    @EqualsAndHashCode.Include
    @Indexed(unique = true)
    private final String model;

    @EqualsAndHashCode.Include
    private int topSpeed;

    public Aircraft(String model, int topSpeed) {
        this.model = model;
        this.topSpeed = topSpeed;
    }

}
