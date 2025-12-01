package com.pizzeria.backend.controller;

import com.pizzeria.backend.entity.Categoria;
import com.pizzeria.backend.repo.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/categorias") @CrossOrigin(origins="*")
public class CategoriaController {
  private final CategoriaRepository repo;
  public CategoriaController(CategoriaRepository repo){ this.repo = repo; }

  @GetMapping public List<Categoria> listar(){ return repo.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Categoria> obtener(@PathVariable Long id){
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria c){
    return ResponseEntity.ok(repo.save(c));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @Valid @RequestBody Categoria c){
    return repo.findById(id).map(ex -> {
      ex.setNombre(c.getNombre());
      ex.setDescripcion(c.getDescripcion());
      ex.setActivo(c.getActivo());
      return ResponseEntity.ok(repo.save(ex));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id){
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id); return ResponseEntity.noContent().build();
  }
}
