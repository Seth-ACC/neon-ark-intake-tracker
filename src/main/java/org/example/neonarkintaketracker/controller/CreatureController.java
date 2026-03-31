// CreatureController.java

package org.example.neonarkintaketracker.controller;

import jakarta.validation.Valid;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.service.CreatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;
import java.util.Optional;

/*
 * This controller handles incoming HTTP requests for /api/creatures
 */
@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureService service;

    // Constructor-based Dependency Injection (DI)
    public CreatureController(CreatureService service) {
        this.service = service;
    }

    /*
     * Map HTTP GET requests at /api/creatures to this method
     * Example: GET http://localhost:8080/api/creatures
     */
    @GetMapping
    public ResponseEntity<List<CreatureResponse>> getAllCreatures() {

        List<CreatureResponse> creatures = service.getAllCreatures();

        // Return 200 OK with JSON body
        return ResponseEntity.ok(creatures);
    }

    // NEW: GET /api/creatures/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getCreatureById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getCreatureById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    @PostMapping
    public ResponseEntity<CreatureResponse> create(@Valid @RequestBody CreatureRequest req) {
        CreatureResponse created = service.createCreature(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}