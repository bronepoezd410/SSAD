package com.bronepoezd.rspp.controllers;

import com.bronepoezd.rspp.model.Knife;
import com.bronepoezd.rspp.repository.KnifeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.bronepoezd.rspp.utils.TraceLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/knives")
public class KnifeController {

    @Autowired
    private KnifeRepository knifeRepository;

    @GetMapping
    public ResponseEntity<List<Knife>> getAllKnives() {
        TraceLogger.verbose("Fetching all knives");
        return ResponseEntity.ok(knifeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Knife> getKnifeById(@PathVariable Long id) {
        Optional<Knife> knife = knifeRepository.findById(id);
        return knife.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Knife> addKnife(@RequestBody Knife knife) {
        if (knife.getName() == null || knife.getMaterial() == null || knife.getLength() == null) {
            return ResponseEntity.badRequest().build();
        }
        Knife savedKnife = knifeRepository.save(knife);
        TraceLogger.verbose("Knife " + knife + " added");
        return ResponseEntity.ok(savedKnife);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Knife> updateKnife(@PathVariable Long id, @RequestBody Knife knife) {
        if (!knifeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        knife.setId(id);
        Knife updatedKnife = knifeRepository.save(knife);
        TraceLogger.verbose("Knife " + id + " updated");

        return ResponseEntity.ok(updatedKnife);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKnife(@PathVariable Long id) {
        if (!knifeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        knifeRepository.deleteById(id);
        TraceLogger.verbose("Knife " + id + " deleted");

        return ResponseEntity.ok("Knife with id " + id + " deleted");
    }
}
