import { EXPIRE_DATE_NONE } from '../../constants';
import { Coordinate, Option } from '../utils';

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

export interface IssuedCoupon {
  id: number;
  customerId: number;
  nickname: string;
  stampCount: number;
  expireDate: string;
  isPrevious: boolean;
  maxStampCount: number;
}

export type CouponActivate = 'current' | 'new';

export interface StampCountOption extends Option {
  value: StampCountOptionValue;
}

export interface ExpireDateOption extends Option {
  value: ExpireDateOptionValue;
}

export type CouponCreated = 'template' | 'custom';

export type StampCountOptionValue = `${number}개`;

export type ExpireDateOptionValue = `${number}개월` | typeof EXPIRE_DATE_NONE;

export type CouponDesignLocation = {
  state: {
    createdType: CouponCreated;
    reward: string;
    expirePeriod: ExpireDateOption;
    stampCount: StampCountOptionValue;
  };
};
