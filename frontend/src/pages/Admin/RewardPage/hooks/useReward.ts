import { useQuery } from '@tanstack/react-query';
import { INVALID_CAFE_ID } from '../../../../constants/magicNumber';
import { getReward } from '../../../../api/get';
import { RewardRes } from '../../../../types/api/response';

const useReward = (cafeId: number, customerId: number) =>
  useQuery<RewardRes, Error>(
    ['getReward'],
    () => {
      return getReward({ params: { customerId, cafeId } });
    },
    {
      enabled: cafeId !== INVALID_CAFE_ID,
      refetchOnWindowFocus: false,
      retry: false,
    },
  );

export default useReward;
