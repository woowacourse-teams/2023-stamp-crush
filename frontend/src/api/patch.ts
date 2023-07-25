import { api } from '.';

export const patchReward = async (customerId: number, rewardId: number) => {
  await api.patch(`/customers/${customerId}/rewards/${rewardId}`, {
    cafeId: 1,
    used: true,
  });
};
