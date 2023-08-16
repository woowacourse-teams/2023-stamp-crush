import { useQuery } from '@tanstack/react-query';
import { getCafe } from '../api/get';

const useCafe = () => {
  const result = useQuery({
    queryKey: ['cafe'],
    queryFn: async () => await getCafe(),
  });

  return result;
};

const useCafeId = () => {
  const result = useCafe();
  if (result.status === 'success') {
    if (result.data.cafes.length === 0) return -1;
    return result.data.cafes[0].id;
  }
  return undefined;
};

export default useCafeId;
