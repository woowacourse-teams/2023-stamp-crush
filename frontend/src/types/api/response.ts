import { Cafe } from '../domain/cafe';
import { SampleImage, SampleBackCouponImage, Coupon, IssuedCoupon } from '../domain/coupon';
import { CustomerPhoneNumber, Customer, CustomerProfile } from '../domain/customer';
import { Reward, RewardHistoryType } from '../domain/reward';
import { StampHistoryType } from '../domain/stamp';

export interface SampleCouponRes {
  sampleFrontImages: SampleImage[];
  sampleBackImages: SampleBackCouponImage[];
  sampleStampImages: SampleImage[];
}

export interface CafeRes {
  cafes: Cafe[];
}

export interface CafeInfoRes {
  cafe: Cafe;
}

export interface CouponRes {
  coupons: Coupon[];
}

export interface CustomerPhoneNumberRes {
  customer: CustomerPhoneNumber[];
}

export interface IssueCouponRes {
  couponId: number;
}

export interface IssuedCouponsRes {
  coupons: IssuedCoupon[];
}

export interface StampHistoryRes {
  stampHistories: StampHistoryType[];
}

export interface CustomersRes {
  customers: Customer[];
}

export interface MyRewardRes {
  rewards: RewardHistoryType[];
}

export interface OAuthJWTRes {
  customerId?: number;
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
  grantType: 'Bearer';
}

export interface CustomerProfileRes {
  profile: CustomerProfile;
}

export interface ImageUploadRes {
  imageUrl: string;
}

export interface RewardRes {
  rewards: Reward[];
}
