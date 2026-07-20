package com.location.creator.persistence;

import com.location.creator.domain.TraySize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrayRepository extends JpaRepository<TrayEntity, Long> {

    List<TrayEntity> findByTraySize(TraySize traySize);
}
