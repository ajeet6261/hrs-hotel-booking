package com.hrs.hotelbooking.builder;

import com.hrs.hotelbooking.model.ServiceContext;

import java.util.List;

public abstract class AbstractBuilder<T> {
    protected abstract List<T> buildEntity(ServiceContext serviceContext);

    public List<T> build(ServiceContext serviceContext) {
        List<T> entities = this.buildEntity(serviceContext);
        return entities;
    }
}
