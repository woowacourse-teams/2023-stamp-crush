import { api } from '.';
import { CouponSettingDto } from '../pages/Admin/CustomCouponDesign';
import { StampFormData } from '../pages/Admin/EarnStamp';
import { CafeFormData } from '../pages/Admin/RegisterCafe';

export const postEarnStamp = async ({
  earningStampCount,
  customerId,
  couponId,
  ownerId,
}: StampFormData) => {
  await api.post(`/customers/${customerId}/coupons/${couponId}/stamps/${ownerId}`, {
    earningStampCount,
  });
};

export const postRegisterUser = async (phoneNumber: string) => {
  await api.post('/temporary-customers', { phoneNumber });
};

export const postIssueCoupon = async (customerId: string) => {
  return await api
    .post(`/customers/${customerId}/coupons`, { cafeId: '1' })
    .then((response) => response.json());
};

export const postCouponSetting = async (cafeId: number, couponConfig: CouponSettingDto) => {
  return await api.post(`/admin/coupon-setting?cafe-id=${cafeId}`, couponConfig);
};

export const postRegisterCafe = async ({
  businessRegistrationNumber,
  name,
  roadAddress,
  detailAddress,
}: CafeFormData) => {
  await api.post('/admin/cafes', {
    businessRegistrationNumber,
    name,
    roadAddress,
    detailAddress,
  });
};

export const postIsFavorites = async (couponId: number, isFavorites: boolean) => {
  await api.post(`/coupons/${couponId}/favorites`, {
    isFavorites,
  });
};
