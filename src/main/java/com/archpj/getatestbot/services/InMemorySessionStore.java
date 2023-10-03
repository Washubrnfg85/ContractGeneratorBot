package com.archpj.getatestbot.services;

import com.archpj.getatestbot.models.Session;

import java.util.HashMap;
import java.util.Map;

public class InMemorySessionStore implements SessionStore {

    private final Map<Long, Session> sessions = new HashMap<>();

    @Override
    public Session get(long employeeId) {
        return sessions.get(employeeId);
    }

    @Override
    public void save(long employeeId, Session session) {
        sessions.put(employeeId, session);
    }

    @Override
    public Session remove(long employeeId) {
        return null;
    }
}
