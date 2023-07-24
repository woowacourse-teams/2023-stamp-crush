import { api } from '.';

export const getCustomer = async (phoneNumber: string) => {
  return await api.get(`/customers?phone-number=${phoneNumber}`);
};

export const getList = async () => {
  return await api.get('/cafes/1/customers');
};

export const getCoupon = async (customerId: string, cafeId: string) => {
  return await api.get(`/customers/${customerId}/coupons?cafeId=${cafeId}&active=true`);
};

export const getReward = async (customerId: number | undefined, cafeId: number) => {
  if (!customerId) {
    throw new Error('잘못된 요청입니다.');
  }

  return await api.get(`/customers/${customerId}/rewards?cafeId=${cafeId}&used=${false}`);
};

export const getCouponSamples = async (maxStampCount: number) => {
  return await api.get(`/coupon-samples?max-stamp-count=${maxStampCount}`);
};
