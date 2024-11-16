package com.ejemplo.hotel.reservas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Integer id, @RequestBody Reserva reservaActualizada) {
        Reserva reservaExistente = reservaRepository.findById(id).orElse(null);
        if (reservaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        reservaExistente.setCampo1(reservaActualizada.getCampo1()); // Actualiza los campos necesarios
        reservaExistente.setCampo2(reservaActualizada.getCampo2());
        // ... otros campos a actualizar ...
        Reserva reservaActualizadaFinal = reservaRepository.save(reservaExistente);
        return ResponseEntity.ok(reservaActualizadaFinal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Integer id) {
        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
