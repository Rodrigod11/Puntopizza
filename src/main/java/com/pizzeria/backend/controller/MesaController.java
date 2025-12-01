package com.pizzeria.backend.controller;

import com.pizzeria.backend.entity.Mesa;
import com.pizzeria.backend.repo.MesaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin
public class MesaController {

  private final MesaRepository repo;
  public MesaController(MesaRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Mesa> listar() { return repo.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Mesa> obtener(@PathVariable Long id) {
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Mesa> crear(@Valid @RequestBody Mesa m) {
    return ResponseEntity.ok(repo.save(m));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Mesa> actualizar(@PathVariable Long id, @Valid @RequestBody Mesa m) {
    return repo.findById(id).map(db -> {
      db.setNumero(m.getNumero());
      db.setCapacidad(m.getCapacidad());
      db.setOcupada(m.getOcupada());
      return ResponseEntity.ok(repo.save(db));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
