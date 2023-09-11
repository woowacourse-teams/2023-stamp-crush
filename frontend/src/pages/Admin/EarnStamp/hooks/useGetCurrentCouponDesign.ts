import { useQuery } from '@tanstack/react-query';
import { getCurrentCouponDesign } from '../../../../api/get';
import { IssuedCouponsRes } from '../../../../types/api';

const useGetCurrentCouponDesign = (cafeId: number, coupon: IssuedCouponsRes | undefined) => {
  return useQuery({
    queryKey: ['couponDesignData', coupon],
    queryFn: () => {
      if (!coupon) throw new Error('쿠폰 정보를 불러오지 못했습니다.');

      return getCurrentCouponDesign({ params: { couponId: coupon.coupons[0].id, cafeId } });
    },
    enabled: !!coupon && coupon.coupons.length !== 0,
  });
};

export default useGetCurrentCouponDesign;
