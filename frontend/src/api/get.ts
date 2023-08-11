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

// FIXME: cafeId 매개변수로 받아와서 처리
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
  return await api.get<RewardRes>(`/rewards?used=${params.used}`, customerHeader);
};

export const getStampHistorys = async () => {
  return await api.get<StampHistoryRes>('/stamp-history');
};
