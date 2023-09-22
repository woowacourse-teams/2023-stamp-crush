import { api, customerHeader, ownerHeader } from '.';
import { PARAMS_ERROR_MESSAGE } from '../constants';
import {
  QueryReq,
  PhoneNumberParams,
  CafeIdParams,
  CustomerIdParams,
  MaxStampCountParams,
  UsedParams,
  OAuthTokenParams,
  CouponIdParams,
} from '../types/api/request';
import {
  CafeRes,
  CustomerPhoneNumberRes,
  CustomersRes,
  IssuedCouponsRes,
  SampleCouponRes,
  CouponRes,
  CafeInfoRes,
  MyRewardRes,
  StampHistoryRes,
  OAuthJWTRes,
  CustomerProfileRes,
  RewardRes,
  CustomerRegisterTypeRes,
} from '../types/api/response';
import { CouponDesign } from '../types/domain/coupon';

// 인증 header가 필요없는 api
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

// 사장모드 api
export const getCafe = async () => {
  return await api.get<CafeRes>('/admin/cafes', ownerHeader());
};

export const getCustomer = async ({ params }: QueryReq<PhoneNumberParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CustomerPhoneNumberRes>(
    `/admin/customers?phone-number=${params.phoneNumber}`,
    ownerHeader(),
  );
};

export const getCustomers = async ({ params }: QueryReq<CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CustomersRes>(`/admin/cafes/${params.cafeId}/customers`, ownerHeader());
};

export const getCoupon = async ({ params }: QueryReq<CustomerIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<IssuedCouponsRes>(
    `/admin/customers/${params.customerId}/coupons?cafe-id=${params.cafeId}&active=true`,
    ownerHeader(),
  );
};

export const getReward = async ({ params }: QueryReq<CustomerIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<RewardRes>(
    `/admin/customers/${params.customerId}/rewards?cafe-id=${params.cafeId}&used=${false}`,
    ownerHeader(),
  );
};

export const getCouponSamples = async ({ params }: QueryReq<MaxStampCountParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<SampleCouponRes>(
    `/admin/coupon-samples?max-stamp-count=${params.maxStampCount}`,
    ownerHeader(),
  );
};

export const getCouponDesign = async ({ params }: QueryReq<CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CouponDesign>(
    `/admin/coupon-setting?cafe-id=${params.cafeId}`,
    ownerHeader(),
  );
};

export const getCurrentCouponDesign = async ({
  params,
}: QueryReq<CouponIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CouponDesign>(
    `/admin/coupon-setting/${params.couponId}?cafe-id=${params.cafeId}`,
    ownerHeader(),
  );
};

// 고객모드 api

export const getCoupons = async () => {
  return await api.get<CouponRes>('/coupons', customerHeader());
};

export const getCafeInfo = async ({ params }: QueryReq<CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CafeInfoRes>(`/cafes/${params.cafeId}`, customerHeader());
};

export const getMyRewards = async ({ params }: QueryReq<UsedParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<MyRewardRes>(`/rewards?used=${params.used}`, customerHeader());
};

export const getStampHistories = async () => {
  return await api.get<StampHistoryRes>('/stamp-histories', customerHeader());
};

export const getCustomerProfile = async () => {
  return await api.get<CustomerProfileRes>('/profiles', customerHeader());
};

export const getCustomerRegisterType = async ({ params }: QueryReq<PhoneNumberParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.get<CustomerRegisterTypeRes>(
    `/customers/profiles/search?phone-number=${params.phoneNumber}`,
    customerHeader(),
  );
};
