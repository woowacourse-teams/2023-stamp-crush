import { useQuery } from '@tanstack/react-query';
import { getCoupons } from '../../../../api/get';

const useGetCoupons = () => {
  return useQuery({
    queryKey: ['coupons'],
    queryFn: getCoupons,
    select: (data) => data.coupons,
  });
};

export default useGetCoupons;
