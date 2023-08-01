export interface SampleImage {
  id: number;
  imageUrl: string;
}

export interface SampleBackCouponImage extends SampleImage {
  stampCoordinates: StampCoordinate[];
}

export interface SampleCouponRes {
  sampleFrontImages: SampleImage[];
  sampleBackImages: SampleBackCouponImage[];
  sampleStampImages: SampleImage[];
}

export interface StampCoordinate {
  order: number;
  xCoordinate: number;
  yCoordinate: number;
}

export interface CafeRes {
  cafe: CafeType;
}

export interface CafeType {
  id: number;
  name: string;
  introduction: string;
  openTime: string;
  closeTime: string;
  telephoneNumber: string;
  cafeImageUrl: string;
  roadAddress: string;
  detailAddress: string;
}

export interface CouponType {
  cafeInfo: {
    id: number;
    name: string;
  };
  couponInfos: [
    {
      id: number;
      isFavorites: boolean;
      stampCount: number;
      maxStampCount: number;
      rewardName: string;
      frontImageUrl: string;
      backImageUrl: string;
      stampImageUrl: string;
      coordinates: StampCoordinate[];
    },
  ];
}

export interface Option {
  key: string;
  value: string;
}

export interface Pos {
  x: number;
  y: number;
}

export interface CouponSettingReq {
  frontImageUrl: string;
  backImageUrl: string;
  stampImageUrl: string;
  coordinates: StampCoordinate[];
  reward: string;
  expirePeriod: number;
  maxStampCount: number;
}
