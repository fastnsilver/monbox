package com.fns.monbox.model;

import org.springframework.data.annotation.Id;

/**
 * Base class for all model classes that has an id property.
 */
public class BaseModel {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
