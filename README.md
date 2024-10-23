POST - /api/email/config

{
    "host": "smtp.gmail.com",
    "port": 587,
    "username": "email@gmail.com",
    "password": "app-specific-password",
    "protocol": "smtp",
    "auth": true,
    "tlsEnable": true
}

GET - /api/email/config

POST - /api/email/send

{
    "to": "receiever@gmail.com",
    "subject": "Test Email Subject",
    "body": "This is a test email body"
}
