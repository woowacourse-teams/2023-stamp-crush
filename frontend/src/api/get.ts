import { api, customerHeader, ownerHeader } from '.';
import { PARAMS_ERROR_MESSAGE } from '../constants';
import {
  CafeIdParams,
  CafeInfoRes,
  CafeRes,
  CouponRes,
  CustomerIdParams,
  CustomerPhoneNumberRes,
  CustomersRes,
  IssuedCouponsRes,
  MaxStampCountParams,
  OAuthJWTRes,
  OAuthTokenParams,
  MyRewardRes,
  PhoneNumberParams,
  QueryReq,
  RewardRes,
  SampleCouponRes,
  StampHistoryRes,
  UsedParams,
} from '../types/api';

export const getCafe = async () => {
  return await api.get<CafeRes>('/admin/cafes', ownerHeader);
};

export const getCustomer = async ({ params }: QueryReq<PhoneNumberParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CustomerPhoneNumberRes>(
    `/admin/customers?phone-number=${params.phoneNumber}`,
    ownerHeader,
  );
};

export const getCustomers = async ({ params }: QueryReq<CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CustomersRes>(`/admin/cafes/${params.cafeId}/customers`, ownerHeader);
};

export const getCoupon = async ({ params }: QueryReq<CustomerIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<IssuedCouponsRes>(
    `/admin/customers/${params.customerId}/coupons?cafe-id=${params.cafeId}&active=true`,
    ownerHeader,
  );
};

export const getReward = async ({ params }: QueryReq<CustomerIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<RewardRes>(
    `/admin/customers/${params.customerId}/rewards?cafe-id=${params.cafeId}&used=${false}`,
    ownerHeader,
  );
};

export const getCouponSamples = async ({ params }: QueryReq<MaxStampCountParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<SampleCouponRes>(
    `/admin/coupon-samples?max-stamp-count=${params.maxStampCount}`,
    ownerHeader,
  );
};

export const getCoupons = async () => {
  return await api.get<CouponRes>('/coupons', customerHeader);
};

export const getCafeInfo = async ({ params }: QueryReq<CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CafeInfoRes>(`/cafes/${params.cafeId}`, customerHeader);
};

export const getMyRewards = async ({ params }: QueryReq<UsedParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<MyRewardRes>(`/rewards?used=${params.used}`, customerHeader);
};

export const getStampHistories = async () => {
  return await api.get<StampHistoryRes>('/stamp-history', customerHeader);
};

export const getAdminOAuthToken = async (
  { params }: QueryReq<OAuthTokenParams>,
  init: RequestInit = {},
) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<OAuthJWTRes>(
    `/admin/login/${params.resourceServer}/token?code=${params.code}`,
    init,
  );
};

export const getOAuthToken = async (
  { params }: QueryReq<OAuthTokenParams>,
  init: RequestInit = {},
) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<OAuthJWTRes>(
    `/login/${params.resourceServer}/token?code=${params.code}`,
    init,
  );
};
