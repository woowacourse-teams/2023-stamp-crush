import { api, customerHeader } from '.';
import { PARAMS_ERROR_MESSAGE } from '../constants/magicString';
import {
  MutateReq,
  StampEarningReqBody,
  CouponIdParams,
  CustomerIdParams,
  RegisterUserReqBody,
  IssueCouponReqBody,
  CouponSettingReqBody,
  CafeIdParams,
  CafeRegisterReqBody,
  IsFavoritesReqBody,
  CustomerLinkDataReqBody,
  AdminAccountDataReqBody,
} from '../types/api/request';
import { ownerInstance } from './axios';

export const postEarnStamp = async ({
  params,
  body,
}: MutateReq<StampEarningReqBody, CouponIdParams & CustomerIdParams>) => {
  if (!params) return;
  await ownerInstance.post<StampEarningReqBody>(
    `/customers/${params.customerId}/coupons/${params.couponId}/stamps`,
    body,
  );
};

export const postTemporaryCustomer = async ({ body }: MutateReq<RegisterUserReqBody>) => {
  await ownerInstance.post<RegisterUserReqBody>('/temporary-customers', body);
};

export const postIssueCoupon = async ({
  params,
  body,
}: MutateReq<IssueCouponReqBody, CustomerIdParams>) => {
  if (!params) return;
  await ownerInstance.post<IssueCouponReqBody>(`/customers/${params.customerId}/coupons`, body);
};

export const postCouponSetting = async ({
  params,
  body,
}: MutateReq<CouponSettingReqBody, CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  await ownerInstance.post<CouponSettingReqBody>(`/coupon-setting?cafe-id=${params.cafeId}`, body);
};

export const postRegisterCafe = async ({ body }: MutateReq<CafeRegisterReqBody>) => {
  await ownerInstance.post<CafeRegisterReqBody>('/cafes', body);
};

export const postUploadImage = async (file: File) => {
  const formData = new FormData();
  formData.append('image', file);

  return await ownerInstance.post('/images', formData);
};

export const postAdminLogin = async ({ body }: MutateReq<AdminAccountDataReqBody>) => {
  return await ownerInstance.post('/login', body);
};

export const postAdminSignUp = async ({ body }: MutateReq<AdminAccountDataReqBody>) => {
  await ownerInstance.post('/owners', body);
};

// 고객모드 api

export const postIsFavorites = async ({
  params,
  body,
}: MutateReq<IsFavoritesReqBody, CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.post<IsFavoritesReqBody>(
    `/cafes/${params.cafeId}/favorites`,
    customerHeader(),
    body,
  );
};

export const postCustomerPhoneNumber = async ({ body }: MutateReq<RegisterUserReqBody>) => {
  return await api.post<RegisterUserReqBody>('/profiles/phone-number', customerHeader(), body);
};

export const postCustomerLinkData = async ({ body }: MutateReq<CustomerLinkDataReqBody>) => {
  return await api.post<CustomerLinkDataReqBody>('/profiles/link-data', customerHeader(), body);
};
