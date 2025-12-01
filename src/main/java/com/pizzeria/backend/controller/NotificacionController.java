package com.pizzeria.backend.controller;

import com.pizzeria.backend.dto.RegistroMailRequest;
import com.pizzeria.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacion")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificacionController {

    @Autowired
    private MailService mailService;

    @PostMapping("/registro")
    public ResponseEntity<?> enviarRegistro(@RequestBody RegistroMailRequest req) {

        mailService.enviarCorreo(
                req.getEmail(),
                "Registro exitoso",
                "Hola " + req.getNombre()
                        + ", gracias por registrarte en Punto Pizza 🍕🔥"
        );

        return ResponseEntity.ok("Correo enviado");
    }
}
