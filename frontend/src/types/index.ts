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
