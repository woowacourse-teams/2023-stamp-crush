import { api, customerHeader, ownerHeader } from '.';

export const getCafe = async () => {
  return await api.get('/admin/cafes', ownerHeader);
};

export const getCustomer = async (phoneNumber: string) => {
  return await api.get(`/admin/customers?phone-number=${phoneNumber}`, ownerHeader);
};

// FIXME: cafeId 매개변수로 받아와서 처리
export const getList = async () => {
  const cafeId = 1;
  return await api.get(`/admin/cafes/${cafeId}/customers`, ownerHeader);
};

export const getCoupon = async (customerId: string, cafeId: string) => {
  return await api.get(
    `/admin/customers/${customerId}/coupons?cafe-id=${cafeId}&active=true`,
    ownerHeader,
  );
};

export const getReward = async (customerId: number | undefined, cafeId: number) => {
  if (!customerId) {
    throw new Error('잘못된 요청입니다.');
  }

  return await api.get(
    `/admin/customers/${customerId}/rewards?cafe-id=${cafeId}&used=${false}`,
    ownerHeader,
  );
};

export const getCouponSamples = async (maxStampCount: number) => {
  return await api.get(`/admin/coupon-samples?max-stamp-count=${maxStampCount}`, ownerHeader);
};

export const getCoupons = async () => {
  return await api.get('/coupons', customerHeader);
};

export const getCafeInfo = async (cafeId: number) => {
  return await api.get(`/cafes/${cafeId}`, customerHeader);
};

export const getMyRewards = async (used: boolean) => {
  return await api.get(`/rewards?used=${used}`, customerHeader);
};
