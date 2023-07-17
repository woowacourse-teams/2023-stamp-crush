package com.stampcrush.backend.entity.sample;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class SampleBackImage extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String imageUrl;

    @OneToMany(mappedBy = "sampleBackImage")
    private List<SampleStampCoordinate> sampleStampCoordinates = new ArrayList<>();

    public SampleBackImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
