package com.archpj.getatestbot.services;

import com.archpj.getatestbot.models.Session;

public class RedisSessionStore implements SessionStore {
    @Override
    public Session get(long employeeId) {
        // ...
    }

    @Override
    public void save(long employeeId, Session session) {
        // ...
    }

    @Override
    public Session remove(long employeeId) {
        return null;
    }
}
