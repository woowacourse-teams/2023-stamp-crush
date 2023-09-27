import { CouponDesign } from '../domain/coupon';
import { RegisterType } from '../domain/customer';

/** react-query request query 추상화 타입 */
export interface QueryReq<K = unknown> {
  params?: K;
}

/** react-query request mutation 추상화 타입 */
export interface MutateReq<T = unknown, K = unknown> {
  body: T;
  params?: K;
}

// params 타입
export interface CustomerTypeParams {
  customerType?: RegisterType;
}

export interface CouponIdParams {
  couponId: number;
}

export interface CafeIdParams {
  cafeId: number;
}

export interface RewardIdParams {
  rewardId: number;
}

export interface PhoneNumberParams {
  phoneNumber: string;
}

export interface MaxStampCountParams {
  maxStampCount: number;
}

export interface UsedParams {
  used: boolean;
}

export interface OAuthTokenParams {
  resourceServer: 'kakao';
  code: string;
}
export interface CustomerIdParams {
  customerId: number;
}

export interface PhoneNumberParams {
  phoneNumber: string;
}

// body 타입
export interface CouponSettingReqBody extends CouponDesign {
  reward: string;
  expirePeriod: number;
  maxStampCount: number;
}

export interface StampEarningReqBody {
  earningStampCount: number;
}

export interface CafeInfoReqBody {
  openTime: string;
  closeTime: string;
  telephoneNumber: string;
  cafeImageUrl: string;
  introduction: string;
}

export interface CafeRegisterReqBody {
  businessRegistrationNumber: string;
  name: string;
  roadAddress: string;
  detailAddress: string;
}

export interface IsFavoritesReqBody {
  isFavorites: boolean;
}

export interface RewardReqBody {
  cafeId: number;
  used: boolean;
}

export interface IssueCouponReqBody {
  cafeId: number;
}

export interface RegisterUserReqBody {
  phoneNumber: string;
}

export interface StampEarningReq {
  earningStampCount: number;
  customerId: string;
  couponId: string;
}

export interface CustomerLinkDataReqBody {
  id: number;
}

export interface AdminAccountDataReqBody {
  loginId: string;
  password: string;
}
