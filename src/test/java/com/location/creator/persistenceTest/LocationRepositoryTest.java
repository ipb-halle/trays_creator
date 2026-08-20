package com.location.creator.persistenceTest;

import com.location.creator.domain.LocationNode;
import com.location.creator.domain.LocationTypes;
import com.location.creator.persistence.LocationEntity;
import com.location.creator.persistence.LocationMapper;
import com.location.creator.persistence.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class LocationRepositoryTest {

    @Autowired
    LocationRepository repo;

    @Autowired
    TestEntityManager tem;


    @Test
    public void findByName_findRefrigeratorByName() {
        LocationNode node = LocationNode.of(LocationTypes.REFRIGERATOR, "K1", "R302");
        LocationEntity entity = LocationMapper.toEntity(node, "room302:12345");
        entity.setEid("fridge:K1");
        repo.save(entity);
        repo.flush();
        tem.clear();
        Optional<LocationEntity> byName = repo.findByName("R302.K1");

        assertThat(byName).isPresent();
        LocationEntity result = byName.get();
        assertThat(result).isNotSameAs(entity);
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(entity);
    }

    @Test
    public void findByNameAndAncestorEid_distinguishesShelvesWithSameName() {
        LocationNode locationNode = LocationNode.of(LocationTypes.SHELF, "3", "R302");
        LocationEntity entity = LocationMapper.toEntity(locationNode, "fidge:12345");
        entity.setEid("schelf:12345");
        repo.save(entity);
        repo.flush();
        tem.clear();

        Optional<LocationEntity> byNameAndAncestorEid = repo.findByNameAndAncestorEid(entity.getName(), entity.getAncestorEid());
        assertThat(byNameAndAncestorEid).isPresent();
        LocationEntity locationEntity = byNameAndAncestorEid.get();
        assertThat(locationEntity).isNotSameAs(entity);
        assertThat(locationEntity).usingRecursiveComparison().ignoringFields("id").isEqualTo(entity);

    }
}
