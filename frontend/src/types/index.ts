export interface SampleImage {
  id: number;
  imageUrl: string;
}

export interface SampleBackCouponImage extends SampleImage {
  stampCoordinates: StampCoordinate[];
}

export interface StampCoordinate extends Coordinate {
  order: number;
}

export interface Cafe {
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

export interface Coupon {
  cafeInfo: {
    id: number;
    name: string;
    isFavorites: boolean;
  };
  couponInfos: CouponInfo[];
}

export interface CouponInfo extends CouponDesign {
  id: number;
  stampCount: number;
  maxStampCount: number;
  rewardName: string;
}

export interface CouponDesign {
  frontImageUrl: string;
  backImageUrl: string;
  stampImageUrl: string;
  coordinates: StampCoordinate[];
}

export interface Option {
  key: string;
  value: string;
}

export interface Coordinate {
  xCoordinate: number;
  yCoordinate: number;
}

export interface StampEarningReq {
  earningStampCount: number;
  customerId: string;
  couponId: string;
}

export interface Reward {
  id: number;
  rewardName: string;
  cafeName: string;
  createdAt: string;
  usedAt: string | null;
}

export interface Time {
  hour: string;
  minute: string;
}

export type CouponActivate = 'current' | 'new';

export type CouponCreated = 'template' | 'custom';

export type RouterPath = `/${string}`;

export interface ParseDateOption {
  year: boolean;
  month: boolean;
  day: boolean;
}
