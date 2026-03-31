// CreatureService.java

package org.example.neonarkintaketracker.service;

import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.entity.Habitat;
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.example.neonarkintaketracker.repository.HabitatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * Thin service for now.
 * Keeps the controller clean and gives us a place to add
 * validation, DTO mapping, and business rules later.
 */
@Service
public class CreatureService {

    private final CreatureRepository repository;
    private final HabitatRepository habitatRepository;
    private static final DateTimeFormatter FORMATTER  = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

    public CreatureService(CreatureRepository repository, HabitatRepository habitatRepository) {
        this.repository = repository;
        this.habitatRepository = habitatRepository;
    }

    /*
     * Return every creature currently in the database.
     * This is the "Read" operation for GET /api/creatures
     */
    public List<CreatureResponse> getAllCreatures() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private CreatureResponse toResponse(
            Creature c) {                              // Internal entity.

        return new CreatureResponse(
                c.getId(),                                 // Extract ID.
                c.getName(),                               // Extract name.
                c.getSpecies(),                            // Extract species.
                c.getDangerLevel(),                        // Extract danger level.
                c.getCondition(),                          // Extract condition.
                c.getNotes(),                              // Extract notes.
                c.getHabitat().getId(),                    // Return habitat as ID.
                c.getCreatedAt().format(FORMATTER)                // Convert timestamp to string.
        );
    }
    public CreatureResponse getCreatureById(Long id) {
        Creature creature = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creature with id " + id + " not found"));

        return toResponse(creature);
    }

    public CreatureResponse createCreature(CreatureRequest req) {

        Optional<Habitat> maybeHabitat = habitatRepository.findById(req.habitatId());
        if (maybeHabitat.isEmpty()) {
            throw new IllegalArgumentException("Habitat with id " + req.habitatId() + " not found");
        }

        // 1) Map request DTO -> entity
         Creature creature = new Creature();
         creature.setName(req.name());
         creature.setSpecies(req.species());
         creature.setDangerLevel(req.dangerLevel());
         creature.setCondition(req.condition());
         creature.setNotes(req.notes() != null ? req.notes() : "No notes provided");
         creature.setHabitat(maybeHabitat.get());

        // 3) Save (DB assigns id, timestamps, etc.)
        Creature saved = repository.save(creature);

        // 4) Map entity -> response DTO
        CreatureResponse res = toResponse(saved);
        // res.setId(saved.getId());
        // res.setName(saved.getName());
        // res.setSpecies(saved.getSpecies());
        // res.setDangerLevel(saved.getDangerLevel());
        // res.setCondition(saved.getCondition());
        // res.setCreatedAt(saved.getCreatedAt());

        return res;
    }

}