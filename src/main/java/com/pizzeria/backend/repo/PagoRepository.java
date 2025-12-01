package com.pizzeria.backend.repo;

import com.pizzeria.backend.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> { }
