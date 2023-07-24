import { api } from '.';
import { StampFormData } from '../pages/Admin/EarnStamp';

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
