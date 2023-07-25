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

export const postCouponSetting = async (couponConfig: CouponSettingDto) => {
  return await api.post('/coupon-setting', couponConfig);
};

export const postRegisterCafe = async ({
  ownerId,
  businessRegistrationNumber,
  name,
  roadAddress,
  detailAddress,
}: CafeFormData) => {
  await api.post(`/cafes/${ownerId}`, {
    businessRegistrationNumber,
    name,
    roadAddress,
    detailAddress,
  });
};
