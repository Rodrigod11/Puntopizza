package com.pizzeria.backend.controller;

import com.pizzeria.backend.dto.CrearPedidoRequest;
import com.pizzeria.backend.dto.PagarRequest;
import com.pizzeria.backend.entity.*;
import com.pizzeria.backend.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepo;
    private final MesaRepository mesaRepo;
    private final ClienteRepository clienteRepo;
    private final PagoRepository pagoRepo;

    public PedidoController(PedidoRepository pedidoRepo,
                            MesaRepository mesaRepo,
                            ClienteRepository clienteRepo,
                            PagoRepository pagoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.mesaRepo = mesaRepo;
        this.clienteRepo = clienteRepo;
        this.pagoRepo = pagoRepo;
    }

    private Pedido getPedidoOr404(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyAuthority('TRABAJADOR','ADMIN')")
    public ResponseEntity<Pedido> crear(@RequestBody CrearPedidoRequest req) {
        Mesa mesa = mesaRepo.findById(req.getMesaId())
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
        if (Boolean.TRUE.equals(mesa.getOcupada())) {
            throw new IllegalStateException("La mesa ya está ocupada");
        }
        Pedido p = new Pedido();
        p.setMesa(mesa);
        p.setFechaPedido(LocalDateTime.now());
        p.setEstado(PedidoEstado.PENDIENTE);

        if (req.getClienteId() != null) {
            Cliente c = clienteRepo.findById(req.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            p.setCliente(c);
        }
        mesa.setOcupada(true);
        Pedido guardado = pedidoRepo.save(p);
        return ResponseEntity.created(URI.create("/api/pedidos/" + guardado.getId())).body(guardado);
    }

    @PatchMapping("/{id}/estado")
    @Transactional
    @PreAuthorize("hasAnyAuthority('TRABAJADOR','ADMIN')")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id,
                                                @RequestParam("nuevo") PedidoEstado nuevo) {
        Pedido p = getPedidoOr404(id);
        if (nuevo == PedidoEstado.PAGADO)
            throw new IllegalArgumentException("Usa /pagar para marcar PAGADO");
        p.setEstado(nuevo);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/{id}/pagar")
    @Transactional
    @PreAuthorize("hasAnyAuthority('CAJERO','ADMIN')")
    public ResponseEntity<Pedido> pagar(@PathVariable Long id, @RequestBody PagarRequest req) {
        Pedido p = getPedidoOr404(id);

        if (p.getEstado() == PedidoEstado.ANULADO)
            throw new IllegalStateException("No puedes pagar un pedido anulado");

        if (p.getEstado() == PedidoEstado.PAGADO)
            return ResponseEntity.ok(p);

        if (req.getMetodo() == null) throw new IllegalArgumentException("Método de pago requerido");
        if (req.getMonto() == null) throw new IllegalArgumentException("Monto requerido");

        Pago pago = new Pago();
        pago.setPedido(p);
        pago.setFechaPago(LocalDateTime.now());
        pago.setMetodo(req.getMetodo());
        pago.setMonto(req.getMonto());
        pagoRepo.save(pago);

        p.setEstado(PedidoEstado.PAGADO);

        if (p.getMesa() != null) {
            p.getMesa().setOcupada(false);
        }
        return ResponseEntity.ok(p);
    }
}
