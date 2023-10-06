import { useQuery } from '@tanstack/react-query';
import { getCouponDesign } from '../../../../api/get';
import { INVALID_CAFE_ID } from '../../../../constants/magicNumber';

const useGetCouponDesign = (cafeId: number) => {
  return useQuery({
    queryKey: ['couponDesign'],
    queryFn: () => getCouponDesign({ params: { cafeId } }),
    enabled: cafeId !== INVALID_CAFE_ID,
  });
};

export default useGetCouponDesign;
