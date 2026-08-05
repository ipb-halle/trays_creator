package com.location.creator.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class LocationTypeFieldEmbeddable {
    @Column(name = "field_id")
    private String eid;
    private String title;
    private boolean required;

}
