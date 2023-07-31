import { api } from '.';
import { CouponSettingDto } from '../pages/Admin/CustomCouponDesign';
import { StampFormData } from '../pages/Admin/EarnStamp';
import { CafeFormData } from '../pages/Admin/RegisterCafe';
import { PostIsFavoritesReq } from '../pages/CouponList';

export const postEarnStamp = async ({ earningStampCount, customerId, couponId }: StampFormData) => {
  await api.post(`/admin/customers/${customerId}/coupons/${couponId}/stamps`, {
    earningStampCount,
  });
};

export const postRegisterUser = async (phoneNumber: string) => {
  await api.post('/admin/temporary-customers', { phoneNumber });
};

export const postIssueCoupon = async (customerId: string) => {
  return await api
    .post(`/admin/customers/${customerId}/coupons`, { cafeId: '1' })
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

export const postIsFavorites = async ({ cafeId, isFavorites }: PostIsFavoritesReq) => {
  await api.post(`/cafes/${cafeId}/favorites`, {
    isFavorites,
  });
};
