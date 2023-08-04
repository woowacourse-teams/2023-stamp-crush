package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;

import java.util.List;

public class SampleCouponFixture {

    public static final SampleFrontImage SAMPLE_FRONT_IMAGE = new SampleFrontImage("frontImageUrl");
    public static final SampleFrontImage SAMPLE_FRONT_IMAGE_SAVED = new SampleFrontImage(1L, "frontImageUrl");
    public static final SampleBackImage SAMPLE_BACK_IMAGE = new SampleBackImage("backImageUrl");
    public static final SampleBackImage SAMPLE_BACK_IMAGE_SAVED = new SampleBackImage(1L, "backImageUrl");
    public static final SampleStampImage SAMPLE_STAMP_IMAGE = new SampleStampImage("sampleStampImageUrl");
    public static final SampleStampImage SAMPLE_STAMP_IMAGE_SAVED = new SampleStampImage(1L, "sampleStampImageUrl");

    public static final List<SampleStampCoordinate> SAMPLE_COORDINATES_SIZE_EIGHT = List.of(
            new SampleStampCoordinate(1, 1, 2),
            new SampleStampCoordinate(2, 2, 2),
            new SampleStampCoordinate(3, 3, 2),
            new SampleStampCoordinate(4, 4, 2),
            new SampleStampCoordinate(5, 1, 1),
            new SampleStampCoordinate(6, 2, 1),
            new SampleStampCoordinate(7, 3, 1),
            new SampleStampCoordinate(8, 4, 1)
    );
}
