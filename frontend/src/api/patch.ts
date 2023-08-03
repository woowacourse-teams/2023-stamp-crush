import { api, ownerHeader } from '.';
import { CafeInfoReq } from '../types/api';

export const patchReward = async (customerId: number, rewardId: number) => {
  await api.patch(`/admin/customers/${customerId}/rewards/${rewardId}`, ownerHeader, {
    cafeId: 1,
    used: true,
  });
};

export const patchCafeInfo = async (
  cafeId: number,
  { openTime, closeTime, telephoneNumber, cafeImageUrl, introduction }: CafeInfoReq,
) => {
  await api.patch(`/admin/cafes/${cafeId}`, ownerHeader, {
    openTime,
    closeTime,
    telephoneNumber,
    cafeImageUrl,
    introduction,
  });
};
