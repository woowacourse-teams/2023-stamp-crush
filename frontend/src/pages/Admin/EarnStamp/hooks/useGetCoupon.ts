import { useQuery } from '@tanstack/react-query';
import { IssuedCouponsRes } from '../../../../types/api';
import { getCoupon } from '../../../../api/get';
import { INVALID_CAFE_ID } from '../../../../constants';
import { CustomerPhoneNumber } from '../../../../types';

const useGetCoupon = (cafeId: number, customer: CustomerPhoneNumber) => {
  return useQuery<IssuedCouponsRes>(['coupon'], {
    queryFn: async () => {
      if (!customer) throw new Error('고객 정보를 불러오지 못했습니다.');
      return await getCoupon({ params: { customerId: customer.id, cafeId } });
    },
    enabled: !!customer && cafeId !== INVALID_CAFE_ID,
  });
};

export default useGetCoupon;
