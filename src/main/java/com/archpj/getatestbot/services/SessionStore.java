package com.archpj.getatestbot.services;

import com.archpj.getatestbot.models.Session;

public interface SessionStore {

    Session get(long employeeId);

    void save(long employeeId, Session session);

    Session remove(long employeeId);
}
