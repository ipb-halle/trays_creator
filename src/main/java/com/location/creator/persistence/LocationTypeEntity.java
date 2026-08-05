package com.location.creator.persistence;


import com.location.creator.domain.LocationTypes;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_types")
public class LocationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String eid;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private LocationTypes type;

    @ElementCollection
    @CollectionTable(
            name = "fields",
            joinColumns = @JoinColumn(name = "inventory_types_id")
    )
    private List<LocationTypeFieldEmbeddable> fields = new ArrayList<>();

}
