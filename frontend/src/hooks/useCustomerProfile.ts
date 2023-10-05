import { useQuery } from '@tanstack/react-query';
import { getCustomerProfile } from '../api/get';

export const useCustomerProfile = () => {
  const { data: customerProfile, status } = useQuery({
    queryKey: ['customerProfile'],
    queryFn: async () => await getCustomerProfile(),
  });

  return { customerProfile, status };
};
