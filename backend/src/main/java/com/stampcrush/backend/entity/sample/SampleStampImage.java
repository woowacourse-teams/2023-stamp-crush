package com.stampcrush.backend.entity.sample;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class SampleStampImage extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String imageUrl;

    public SampleStampImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SampleStampImage(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    protected SampleStampImage() {
    }
}
