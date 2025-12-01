package com.pizzeria.backend.controller;

import com.pizzeria.backend.entity.Cliente;
import com.pizzeria.backend.repo.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/clientes") @CrossOrigin(origins="*")
public class ClienteController {
  private final ClienteRepository repo;
  public ClienteController(ClienteRepository repo){ this.repo = repo; }

  @GetMapping public List<Cliente> listar(){ return repo.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> obtener(@PathVariable Long id){
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente c){
    return ResponseEntity.ok(repo.save(c));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente c){
    return repo.findById(id).map(ex -> {
      ex.setNombre(c.getNombre());
      ex.setTelefono(c.getTelefono());
      ex.setEmail(c.getEmail());
      ex.setDireccion(c.getDireccion());
      return ResponseEntity.ok(repo.save(ex));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id){
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id); return ResponseEntity.noContent().build();
  }
}
