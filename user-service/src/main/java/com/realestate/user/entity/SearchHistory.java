package com.realestate.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "search_query")
    private String searchQuery;

    @Column(name = "search_filters", columnDefinition = "TEXT")
    private String searchFilters;

    @Column(name = "results_count")
    private Integer resultsCount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}