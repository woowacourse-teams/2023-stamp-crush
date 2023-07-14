package com.stampcrush.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class SampleStampCoordinate extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer stampOrder;

    private Integer xCoordinate;

    private Integer yCoordinate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sample_back_image_id")
    private SampleBackImage sampleBackImage;
}
