package com.location.creator.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByName(String name);

    Optional<LocationEntity> findByNameAndAncestorEid(String name, String ancestorEid);
}
