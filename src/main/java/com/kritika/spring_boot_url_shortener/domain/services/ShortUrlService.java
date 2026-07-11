package com.kritika.spring_boot_url_shortener.domain.services;

import com.kritika.spring_boot_url_shortener.ApplicationProperties;
import com.kritika.spring_boot_url_shortener.domain.entities.ShortUrl;
import com.kritika.spring_boot_url_shortener.domain.models.CreateShortUrlCmd;
import com.kritika.spring_boot_url_shortener.domain.models.PagedResult;
import com.kritika.spring_boot_url_shortener.domain.models.ShortUrlDto;
import com.kritika.spring_boot_url_shortener.domain.repositories.ShortUrlRepository;
import com.kritika.spring_boot_url_shortener.domain.repositories.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final EntityMapper entityMapper;
    private final ApplicationProperties properties;
    private final UserRepository userRepository;

    public ShortUrlService(
            ShortUrlRepository shortUrlRepository,
            EntityMapper entityMapper,
            ApplicationProperties properties,
            UserRepository userRepository) {

        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
        this.properties = properties;
        this.userRepository = userRepository;
    }

    public PagedResult<ShortUrlDto> findAllPublicShortUrls(
            int pageNo,
            int pageSize) {

        Pageable pageable = getPageable(pageNo, pageSize);

        Page<ShortUrlDto> shortUrlDtoPage =
                shortUrlRepository
                        .findPublicShortUrls(pageable)
                        .map(entityMapper::toShortUrlDto);

        return PagedResult.from(shortUrlDtoPage);
    }

    public PagedResult<ShortUrlDto> getUserShortUrls(
            Long userId,
            int page,
            int pageSize) {

        Pageable pageable = getPageable(page, pageSize);

        Page<ShortUrlDto> shortUrlsPage =
                shortUrlRepository
                        .findByCreatedById(userId, pageable)
                        .map(entityMapper::toShortUrlDto);

        return PagedResult.from(shortUrlsPage);
    }

    @Transactional
    public void deleteUserShortUrls(
            List<Long> ids,
            Long userId) {

        if (ids != null && !ids.isEmpty() && userId != null) {
            shortUrlRepository.deleteByIdInAndCreatedById(ids, userId);
        }
    }

    public PagedResult<ShortUrlDto> findAllShortUrls(
            int page,
            int pageSize) {

        Pageable pageable = getPageable(page, pageSize);

        Page<ShortUrlDto> shortUrlsPage =
                shortUrlRepository
                        .findAllShortUrls(pageable)
                        .map(entityMapper::toShortUrlDto);

        return PagedResult.from(shortUrlsPage);
    }

    private Pageable getPageable(int page, int size) {
        page = page > 1 ? page - 1 : 0;

        return PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "createdAt"
        );
    }

    @Transactional
    public ShortUrlDto createShortUrl(
            CreateShortUrlCmd cmd) {

        String shortKey = generateUniqueShortKey();

        ShortUrl shortUrl = new ShortUrl();

        shortUrl.setOriginalUrl(cmd.originalUrl());
        shortUrl.setShortKey(shortKey);

        if (cmd.userId() == null) {
            shortUrl.setCreatedBy(null);
            shortUrl.setIsPrivate(false);

            shortUrl.setExpiresAt(
                    Instant.now().plus(
                            properties.defaultExpiryInDays(),
                            DAYS
                    )
            );
        } else {
            shortUrl.setCreatedBy(
                    userRepository.findById(cmd.userId()).orElseThrow()
            );

            shortUrl.setIsPrivate(
                    cmd.isPrivate() != null && cmd.isPrivate()
            );

            shortUrl.setExpiresAt(
                    cmd.expirationInDays() != null
                            ? Instant.now().plus(
                            cmd.expirationInDays(),
                            DAYS)
                            : null
            );
        }

        shortUrl.setClickCount(0L);
        shortUrl.setCreatedAt(Instant.now());

        shortUrlRepository.save(shortUrl);

        return entityMapper.toShortUrlDto(shortUrl);
    }

    @Transactional
    public Optional<ShortUrlDto> accessShortUrl(
            String shortKey,
            Long userId) {

        Optional<ShortUrl> shortUrlOptional =
                shortUrlRepository.findByShortKey(shortKey);

        if (shortUrlOptional.isEmpty()) {
            return Optional.empty();
        }

        ShortUrl shortUrl = shortUrlOptional.get();

        if (shortUrl.getExpiresAt() != null &&
                shortUrl.getExpiresAt().isBefore(Instant.now())) {
            return Optional.empty();
        }

        if (Boolean.TRUE.equals(shortUrl.getIsPrivate())
                && shortUrl.getCreatedBy() != null
                && !Objects.equals(
                shortUrl.getCreatedBy().getId(),
                userId)) {

            return Optional.empty();
        }

        shortUrl.setClickCount(
                shortUrl.getClickCount() + 1
        );

        shortUrlRepository.save(shortUrl);

        return shortUrlOptional.map(
                entityMapper::toShortUrlDto
        );
    }

    private String generateUniqueShortKey() {

        String shortKey;

        do {
            shortKey = generateRandomShortKey();
        } while (
                shortUrlRepository.existsByShortKey(shortKey)
        );

        return shortKey;
    }

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int SHORT_KEY_LENGTH = 6;

    private static final SecureRandom RANDOM =
            new SecureRandom();

    public static String generateRandomShortKey() {

        StringBuilder sb =
                new StringBuilder(SHORT_KEY_LENGTH);

        for (int i = 0; i < SHORT_KEY_LENGTH; i++) {
            sb.append(
                    CHARACTERS.charAt(
                            RANDOM.nextInt(
                                    CHARACTERS.length()
                            )
                    )
            );
        }

        return sb.toString();
    }

    public long getTotalUrls() {
        return shortUrlRepository.count();
    }

    public Long getTotalClicks() {
        return shortUrlRepository.getTotalClicks();
    }

    public List<ShortUrlDto> getTopClickedUrls() {
        return shortUrlRepository
                .findTop5ByOrderByClickCountDesc()
                .stream()
                .map(entityMapper::toShortUrlDto)
                .toList();
    }
}