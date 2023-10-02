import { api, customerHeader } from '.';

export const deleteCoupon = async (couponId: number) => {
  return await api.delete(`/coupons/${couponId}`, customerHeader());
};

export const deleteCustomer = async () => {
  return await api.delete('/customers', customerHeader());
};
