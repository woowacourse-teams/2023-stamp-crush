import { SampleImage, SampleBackCouponImage, Cafe, Coupon, CouponDesign, Reward } from '.';

export interface SampleCouponRes {
  sampleFrontImages: SampleImage[];
  sampleBackImages: SampleBackCouponImage[];
  sampleStampImages: SampleImage[];
}

export interface CafeRes {
  cafe: Cafe;
}

export interface CouponSettingReq extends CouponDesign {
  reward: string;
  expirePeriod: number;
  maxStampCount: number;
}

export interface CustomerRes {
  id: number;
  nickname: string;
  stampCount: number;
  maxStampCount: number;
  rewardCount: number;
  visitCount: number;
  firstVisitDate: string;
  isRegistered: boolean;
}

export interface StampEarningReq {
  earningStampCount: number;
  customerId: string;
  couponId: string;
}

export interface CafeInfoReq {
  openTime: string;
  closeTime: string;
  telephoneNumber: string;
  cafeImageUrl: string;
  introduction: string;
}

export interface CafeRegisterReq {
  businessRegistrationNumber: string;
  name: string;
  roadAddress: string;
  detailAddress: string;
}

export interface RewardRes {
  id: number;
  name: string;
}

export interface PostIsFavoritesReq {
  cafeId: number;
  isFavorites: boolean;
}

export interface CouponRes {
  coupons: Coupon[];
}

export interface MyRewardRes {
  rewards: Reward[];
}
