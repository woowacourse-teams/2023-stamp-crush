import { api } from '.';

export const getCafe = async () => {
  return await api.get('/admin/cafes');
};

export const getCustomer = async (phoneNumber: string) => {
  return await api.get(`/admin/customers?phone-number=${phoneNumber}`);
};

// FIXME: cafeId 매개변수로 받아와서 처리
export const getList = async () => {
  const cafeId = 1;
  return await api.get(`/admin/cafes/${cafeId}/customers`);
};

export const getCoupon = async (customerId: string, cafeId: string) => {
  return await api.get(`/admin/customers/${customerId}/coupons?cafe-id=${cafeId}&active=true`);
};

export const getReward = async (customerId: number | undefined, cafeId: number) => {
  if (!customerId) {
    throw new Error('잘못된 요청입니다.');
  }

  return await api.get(`/admin/customers/${customerId}/rewards?cafe-id=${cafeId}&used=${false}`);
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
