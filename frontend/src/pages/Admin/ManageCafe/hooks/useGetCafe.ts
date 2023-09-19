import { useQuery } from '@tanstack/react-query';
import { getCafe } from '../../../../api/get';

const useGetCafe = () => {
  return useQuery(['cafe'], () => getCafe());
};

export default useGetCafe;
