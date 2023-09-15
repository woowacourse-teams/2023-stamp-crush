import { useLocation } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { parseStampCount } from '../../../../../utils';
import { getCouponSamples } from '../../../../../api/get';
import { SampleCouponRes } from '../../../../../types/api/response';

export const useSampleImages = () => {
  const location = useLocation();
  const maxStampCount = parseStampCount(location.state.stampCount);

  return useQuery<SampleCouponRes>(
    ['coupon-samples', maxStampCount],
    () => getCouponSamples({ params: { maxStampCount } }),
    {
      staleTime: Infinity,
    },
  );
};
