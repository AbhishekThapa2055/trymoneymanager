//package uk.abhishek.moneymanager.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//
//public class EmailService {
//    private  final JavaMailSender mailSender;
//    @Value("${spring.mail.properties.mail.smtp.from}")
//    private String fromEmail;
//    public void sendEmail (String to, String subject , String body)
//    {
//try {
//    SimpleMailMessage message = new SimpleMailMessage();
//    message.setFrom(fromEmail);
//    message.setTo(to);
//    message.setSubject(subject);
//    message.setText(body);
//    mailSender.send(message);
//
//}catch (Exception e)
//{
//    throw new RuntimeException(e.getMessage());
//}
//    }
//
//}
//
//
////package uk.abhishek.moneymanager.service;
////
////import lombok.RequiredArgsConstructor;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.http.*;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestTemplate;
////
////import java.util.Map;
////
////@Service
////@RequiredArgsConstructor
////public class EmailService {
////
////    @Value("${brevo.api.key}")
////    private String apiKey;
////
////    @Value("${brevo.from.email}")
////    private String fromEmail;
////
////    @Value("${brevo.from.name}")
////    private String fromName;
////
////    private final RestTemplate restTemplate = new RestTemplate();
////
////    public void sendEmail(String to, String subject, String body) {
////        String url = "https://api.brevo.com/v3/smtp/email";
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        headers.set("api-key", apiKey);
////
////        Map<String, Object> requestBody = Map.of(
////                "sender", Map.of(
////                        "email", fromEmail,
////                        "name", fromName
////                ),
////                "to", new Object[]{
////                        Map.of("email", to)
////                },
////                "subject", subject,
////                "textContent", body
////        );
////
////        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
////
////        try {
////            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
////            System.out.println("Email sent: " + response.getBody());
////        } catch (Exception e) {
////            throw new RuntimeException("Failed to send email: " + e.getMessage());
////        }
////    }
////}