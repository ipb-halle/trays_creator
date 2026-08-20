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
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        LocationNode ln1 = LocationNode.of(LocationTypes.SHELF, "3", "R302");
        LocationNode ln2 = LocationNode.of(LocationTypes.SHELF, "3", "R2-208");
        LocationEntity le1 = LocationMapper.toEntity(ln1, "fridge1:12345");
        LocationEntity le2 = LocationMapper.toEntity(ln2, "fridge2:12345");
        le1.setEid("shelf1:12345");
        le2.setEid("shelf2:12345");
        repo.save(le1);
        repo.save(le2);
        repo.flush();
        tem.clear();

        Optional<LocationEntity> ler1 = repo.findByNameAndAncestorEid("3", "fridge1:12345");
        assertThat(ler1).isPresent();
        LocationEntity locationEntity = ler1.get();
        assertThat(locationEntity).isNotSameAs(le1);
        assertThat(locationEntity.getName()).isEqualTo("3");
        assertThat(locationEntity).usingRecursiveComparison().ignoringFields("id").isEqualTo(le1);

    }
    
    @Test
    public void findByName_showThat_MovableLocationsThrowsException(){
        LocationNode ln1 = LocationNode.of(LocationTypes.SHELF, "3", "R302");
        LocationNode ln2 = LocationNode.of(LocationTypes.SHELF, "3", "R2-208");
        LocationEntity le1 = LocationMapper.toEntity(ln1, "fridge1:12345");
        LocationEntity le2 = LocationMapper.toEntity(ln2, "fridge2:12345");
        le1.setEid("shelf1:12345");
        le2.setEid("shelf2:12345");
        repo.save(le1);
        repo.save(le2);
        repo.flush();
        tem.clear();

        assertThatThrownBy(()->repo.findByName("3")).isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }

    @Test
    void findByName_returnsEmptyForUnknownName(){
        Optional<LocationEntity> byName = repo.findByName("R999.K9");
        assertThat(byName).isEmpty();
    }
}
