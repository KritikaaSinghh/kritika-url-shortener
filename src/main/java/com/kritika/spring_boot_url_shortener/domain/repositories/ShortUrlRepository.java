package com.kritika.spring_boot_url_shortener.domain.repositories;

import com.kritika.spring_boot_url_shortener.domain.entities.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    @Query("""
            select su
            from ShortUrl su
            left join fetch su.createdBy
            where su.isPrivate = false
            """)
    Page<ShortUrl> findPublicShortUrls(Pageable pageable);

    boolean existsByShortKey(String shortKey);

    Optional<ShortUrl> findByShortKey(String shortKey);

    Page<ShortUrl> findByCreatedById(Long userId, Pageable pageable);

    @Modifying
    void deleteByIdInAndCreatedById(List<Long> ids, Long userId);

    @Query("""
            select su
            from ShortUrl su
            left join fetch su.createdBy
            """)
    Page<ShortUrl> findAllShortUrls(Pageable pageable);

    List<ShortUrl> findTop5ByOrderByClickCountDesc();

    @Query("""
            SELECT COALESCE(SUM(su.clickCount),0)
            FROM ShortUrl su
            """)
    Long getTotalClicks();
}