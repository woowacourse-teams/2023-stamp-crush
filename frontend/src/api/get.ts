import { api, customerHeader } from '.';
import { PARAMS_ERROR_MESSAGE } from '../constants/magicString';

import {
  QueryReq,
  PhoneNumberParams,
  CafeIdParams,
  CustomerIdParams,
  MaxStampCountParams,
  UsedParams,
  OAuthTokenParams,
  CouponIdParams,
  CustomerTypeParams,
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
import { instance, ownerInstance } from './axios';

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

export const getReissuedToken = async () => {
  return (await instance.get('/admin/auth/reissue-token')).data;
};

// 사장모드 api
export const getCafe = async () => {
  return (await ownerInstance.get<CafeRes>('/cafes')).data;
};

export const getCustomer = async ({ params }: QueryReq<PhoneNumberParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return (
    await ownerInstance.get<CustomerPhoneNumberRes>(`/customers?phone-number=${params.phoneNumber}`)
  ).data;
};

export const getCustomers = async ({ params }: QueryReq<CafeIdParams & CustomerTypeParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  const requestUrl = params.customerType
    ? `/cafes/${params.cafeId}/customers?customer-type=${params.customerType}`
    : `/cafes/${params.cafeId}/customers`;

  return (await ownerInstance.get<CustomersRes>(requestUrl)).data;
};

export const getCoupon = async ({ params }: QueryReq<CustomerIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return (
    await ownerInstance.get<IssuedCouponsRes>(
      `/customers/${params.customerId}/coupons?cafe-id=${params.cafeId}&active=true`,
    )
  ).data;
};

export const getReward = async ({ params }: QueryReq<CustomerIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return (
    await ownerInstance.get<RewardRes>(
      `/customers/${params.customerId}/rewards?cafe-id=${params.cafeId}&used=${false}`,
    )
  ).data;
};

export const getCouponSamples = async ({ params }: QueryReq<MaxStampCountParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return (
    await ownerInstance.get<SampleCouponRes>(
      `/coupon-samples?max-stamp-count=${params.maxStampCount}`,
    )
  ).data;
};

export const getCouponDesign = async ({ params }: QueryReq<CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return (await ownerInstance.get<CouponDesign>(`/coupon-setting?cafe-id=${params.cafeId}`)).data;
};

export const getCurrentCouponDesign = async ({
  params,
}: QueryReq<CouponIdParams & CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return (
    await ownerInstance.get<CouponDesign>(
      `/coupon-setting/${params.couponId}?cafe-id=${params.cafeId}`,
    )
  ).data;
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
    `/profiles/search?phone-number=${params.phoneNumber}`,
    customerHeader(),
  );
};
