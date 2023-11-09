import { useQuery } from '@tanstack/react-query';
import { getCafeInfo } from '../../../../api/get';

const useGetCafeInfo = (cafeId: number) =>
  useQuery(['cafeInfos', cafeId], {
    queryFn: () => getCafeInfo({ params: { cafeId } }),
    enabled: cafeId !== 0,
    select: (data) => data.cafe,
  });

export default useGetCafeInfo;
