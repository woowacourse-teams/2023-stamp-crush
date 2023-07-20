package com.stampcrush.backend.entity.sample;

import com.stampcrush.backend.entity.baseentity.BaseDate;
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

    public SampleStampCoordinate(Integer stampOrder, Integer xCoordinate, Integer yCoordinate, SampleBackImage sampleBackImage) {
        this.stampOrder = stampOrder;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.sampleBackImage = sampleBackImage;
    }

    public SampleStampCoordinate(Integer stampOrder, Integer xCoordinate, Integer yCoordinate) {
        this(stampOrder, xCoordinate, yCoordinate, null);
    }

    public void setSampleBackImage(SampleBackImage sampleBackImage) {
        this.sampleBackImage = sampleBackImage;
    }

    protected SampleStampCoordinate() {
    }
}
