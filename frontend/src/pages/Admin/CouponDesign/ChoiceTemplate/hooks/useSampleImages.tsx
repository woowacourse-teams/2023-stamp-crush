import { useLocation } from 'react-router-dom';
import { parseStampCount } from '../../../../../utils';
import { SampleCouponRes } from '../../../../../types/api';
import { useQuery } from '@tanstack/react-query';
import { getCouponSamples } from '../../../../../api/get';

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
