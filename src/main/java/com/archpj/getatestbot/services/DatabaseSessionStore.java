package com.archpj.getatestbot.services;

import com.archpj.getatestbot.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSessionStore implements SessionStore {

    // This class exists just for demonstration purpose.
    private interface SessionRepository extends JpaRepository<Session, Long> {

    }

    private final SessionRepository repository;

    @Autowired
    public DatabaseSessionStore(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Session get(long employeeId) {
        return repository.findById(employeeId).orElse(null);
    }

    @Override
    public void save(long employeeId, Session session) {
        repository.save(session);
    }

    @Override
    public Session remove(long employeeId) {
        return null;
    }
}
