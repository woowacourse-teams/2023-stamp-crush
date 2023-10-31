import { useQuery } from '@tanstack/react-query';
import { getCoupons } from '../../../../api/get';

const useGetCoupons = (isCollected: boolean) => {
  return useQuery({
    queryKey: ['coupons'],
    queryFn: getCoupons,
    select: (data) => {
      if (isCollected)
        return data.coupons.filter((coupon) => coupon.cafeInfo.isFavorites && coupon);
      return data.coupons;
    },
  });
};

export default useGetCoupons;
