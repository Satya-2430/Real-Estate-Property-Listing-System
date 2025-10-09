package com.realestate.dashboard.controller;

import com.realestate.dashboard.entity.SavedSearch;
import com.realestate.dashboard.service.SavedSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/saved-searches")
@RequiredArgsConstructor

public class SavedSearchController {

    private final SavedSearchService savedSearchService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavedSearch>> getUserSavedSearches(@PathVariable Long userId) {
        List<SavedSearch> searches = savedSearchService.getUserSavedSearches(userId);
        return ResponseEntity.ok(searches);
    }

    @PostMapping
    public ResponseEntity<SavedSearch> createSavedSearch(@RequestBody SavedSearch savedSearch) {
        SavedSearch created = savedSearchService.createSavedSearch(savedSearch);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedSearch(@PathVariable Long id) {
        savedSearchService.deleteSavedSearch(id);
        return ResponseEntity.ok().build();
    }
}