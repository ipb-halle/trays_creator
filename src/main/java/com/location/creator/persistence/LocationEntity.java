package com.location.creator.persistence;

import com.location.creator.domain.LocationTypes;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations", indexes = {@Index(name="idx_locations_name", columnList = "name")})
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String eid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationTypes type;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column
    private Boolean movable;

    @Column(name = "ancestor_eid")
    private String ancestorEid;

}
