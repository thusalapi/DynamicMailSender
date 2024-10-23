package org.mail.maildemonew.controller;

import org.mail.maildemonew.dto.EmailRequest;
import org.mail.maildemonew.model.EmailConfig;
import org.mail.maildemonew.repository.EmailConfigRepository;
import org.mail.maildemonew.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/email")
public class EmailConfigController {

    @Autowired
    private EmailConfigRepository emailConfigRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/config")
    public ResponseEntity<EmailConfig> saveConfig(@RequestBody EmailConfig config) {
        // Deactivate current active config if exists
        EmailConfig activeConfig = emailConfigRepository.findByIsActiveTrue();
        if (activeConfig != null) {
            activeConfig.setActive(false);
            emailConfigRepository.save(activeConfig);
        }

        config.setActive(true);
        config.setCreatedAt(LocalDateTime.now());
        EmailConfig savedConfig = emailConfigRepository.save(config);
        return ResponseEntity.ok(savedConfig);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.ok("Email sent successfully");
    }

    @GetMapping("/config")
    public ResponseEntity<EmailConfig> getActiveConfig() {
        EmailConfig config = emailConfigRepository.findByIsActiveTrue();
        return ResponseEntity.ok(config);
    }
}