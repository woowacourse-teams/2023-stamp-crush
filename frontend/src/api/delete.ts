import { api, customerHeader } from '.';

export const deleteCoupon = async (couponId: number) => {
  return await api.delete(`/coupons/${couponId}`, customerHeader());
};
