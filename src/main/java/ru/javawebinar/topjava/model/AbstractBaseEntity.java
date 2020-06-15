package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractBaseEntity {
    protected Integer id;
    protected final AtomicInteger counter = new AtomicInteger(0);

    protected AbstractBaseEntity() {
        this.id = counter.incrementAndGet();
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}