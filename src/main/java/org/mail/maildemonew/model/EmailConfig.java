package org.mail.maildemonew.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email_config")
public class EmailConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;
    private int port;
    private String username;
    private String password;
    private String protocol;
    private boolean auth;
    private boolean tlsEnable;
    private boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}