package com.realestate.user.repository;

import com.realestate.user.entity.BuyerPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyerPreferenceRepository extends JpaRepository<BuyerPreference, Long> {
    List<BuyerPreference> findByUserId(Long userId);
}