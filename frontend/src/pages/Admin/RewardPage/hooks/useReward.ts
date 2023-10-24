import { useQuery } from '@tanstack/react-query';
import { INVALID_CAFE_ID } from '../../../../constants/magicNumber';
import { getReward } from '../../../../api/get';

const useReward = (cafeId: number, customerId: number) =>
  useQuery(
    ['getReward'],
    () => {
      return getReward({ params: { customerId, cafeId } });
    },
    {
      enabled: cafeId !== INVALID_CAFE_ID,
    },
  );

export default useReward;
