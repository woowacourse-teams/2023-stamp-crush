import { api } from '.';

export interface CafeInfoBody {
  openTime: string;
  closeTime: string;
  telephoneNumber: string;
  cafeImageUrl: string;
  introduction: string;
}

export const patchReward = async (customerId: number, rewardId: number) => {
  await api.patch(`/customers/${customerId}/rewards/${rewardId}`, {
    cafeId: 1,
    used: true,
  });
};

export const patchCafeInfo = async (
  cafeId: number,
  { openTime, closeTime, telephoneNumber, cafeImageUrl, introduction }: CafeInfoBody,
) => {
  await api.patch(`/admin/cafes/${cafeId}`, {
    openTime,
    closeTime,
    telephoneNumber,
    cafeImageUrl,
    introduction,
  });
};
