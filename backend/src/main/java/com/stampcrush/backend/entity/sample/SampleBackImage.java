package com.stampcrush.backend.entity.sample;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class SampleBackImage extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String imageUrl;

    @OneToMany(mappedBy = "sampleBackImage")
    List<SampleStampCoordinate> sampleStampCoordinates = new ArrayList<>();

    public SampleBackImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected SampleBackImage() {
    }

    public void addCoordinates(SampleStampCoordinate coordinate) {
        coordinate.setSampleBackImage(this);
        sampleStampCoordinates.add(coordinate);
    }
}
