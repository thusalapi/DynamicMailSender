package org.mail.maildemonew.repository;

import org.mail.maildemonew.model.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigRepository extends JpaRepository<EmailConfig, Long> {
    EmailConfig findByIsActiveTrue();
}
