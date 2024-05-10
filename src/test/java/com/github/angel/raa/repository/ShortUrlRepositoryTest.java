package com.github.angel.raa.repository;

import com.github.angel.raa.persistence.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ShortUrlRepositoryTest {
    private final ShortUrlRepository shortUrlRepository;

    @Autowired
    public ShortUrlRepositoryTest(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }


}
