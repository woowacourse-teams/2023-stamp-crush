import { api, customerHeader, ownerHeader } from '.';
import {
  CouponSettingReq,
  StampEarningReq,
  CafeRegisterReq,
  PostIsFavoritesReq,
} from '../types/api';

export const postEarnStamp = async ({
  earningStampCount,
  customerId,
  couponId,
}: StampEarningReq) => {
  await api.post(`/admin/customers/${customerId}/coupons/${couponId}/stamps`, ownerHeader, {
    earningStampCount,
  });
};

export const postRegisterUser = async (phoneNumber: string) => {
  await api.post('/admin/temporary-customers', ownerHeader, { phoneNumber });
};

export const postIssueCoupon = async (customerId: number) => {
  return await api
    .post(`/admin/customers/${customerId}/coupons`, ownerHeader, { cafeId: '1' })
    .then((response) => response.json());
};

export const postCouponSetting = async (cafeId: number, couponConfig: CouponSettingReq) => {
  return await api.post(`/admin/coupon-setting?cafe-id=${cafeId}`, ownerHeader, couponConfig);
};

export const postRegisterCafe = async ({
  businessRegistrationNumber,
  name,
  roadAddress,
  detailAddress,
}: CafeRegisterReq) => {
  await api.post('/admin/cafes', ownerHeader, {
    businessRegistrationNumber,
    name,
    roadAddress,
    detailAddress,
  });
};

export const postIsFavorites = async ({ cafeId, isFavorites }: PostIsFavoritesReq) => {
  await api.post(`/cafes/${cafeId}/favorites`, customerHeader, {
    isFavorites,
  });
};
