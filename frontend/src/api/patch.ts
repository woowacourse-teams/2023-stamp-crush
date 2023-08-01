import { api } from '.';
import { CafeInfoReq } from '../types';

export const patchReward = async (customerId: number, rewardId: number) => {
  await api.patch(`/customers/${customerId}/rewards/${rewardId}`, {
    cafeId: 1,
    used: true,
  });
};

export const patchCafeInfo = async (
  cafeId: number,
  { openTime, closeTime, telephoneNumber, cafeImageUrl, introduction }: CafeInfoReq,
) => {
  await api.patch(`/admin/cafes/${cafeId}`, {
    openTime,
    closeTime,
    telephoneNumber,
    cafeImageUrl,
    introduction,
  });
};
