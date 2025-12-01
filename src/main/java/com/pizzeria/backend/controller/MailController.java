package com.pizzeria.backend.controller;

import com.pizzeria.backend.service.MailService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para enviar correos.
 * Ruta: /api/mail/enviar
 */
@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    // DTO embebido para NO crear más archivos
    public static class MailRequest {
        @NotBlank @Email
        private String para;
        @NotBlank
        private String asunto;
        @NotBlank
        private String mensaje;

        public String getPara() { return para; }
        public void setPara(String para) { this.para = para; }
        public String getAsunto() { return asunto; }
        public void setAsunto(String asunto) { this.asunto = asunto; }
        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    }

    @PostMapping("/enviar")
    public ResponseEntity<Map<String, Object>> enviar(@RequestBody MailRequest req) {
        mailService.enviarCorreo(req.getPara(), req.getAsunto(), req.getMensaje());
        Map<String, Object> resp = new HashMap<>();
        resp.put("ok", true);
        resp.put("msg", "Correo enviado");
        return ResponseEntity.ok(resp);
    }
}
