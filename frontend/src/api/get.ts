import { api } from '.';

export const getCafe = async () => {
  return await api.get('/admin/cafes');
};

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
  return await api.get(`/admin/coupon-samples?max-stamp-count=${maxStampCount}`);
};

export const getCoupons = async () => {
  return await api.get('/coupons');
};

export const getCafeInfo = async (cafeId: number) => {
  return await api.get(`/cafes/${cafeId}`);
};
