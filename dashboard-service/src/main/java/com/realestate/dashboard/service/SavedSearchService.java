package com.realestate.dashboard.service;

import com.realestate.dashboard.entity.SavedSearch;
import com.realestate.dashboard.repository.SavedSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedSearchService {

    private final SavedSearchRepository savedSearchRepository;

    public List<SavedSearch> getUserSavedSearches(Long userId) {
        return savedSearchRepository.findByUserIdAndIsActiveTrue(userId);
    }

    public SavedSearch createSavedSearch(SavedSearch savedSearch) {
        return savedSearchRepository.save(savedSearch);
    }

    public void deleteSavedSearch(Long id) {
        SavedSearch search = savedSearchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Saved search not found"));
        search.setIsActive(false);
        savedSearchRepository.save(search);
    }
}