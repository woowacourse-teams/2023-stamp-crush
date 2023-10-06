import { useQuery } from '@tanstack/react-query';
import { getCustomerProfile } from '../api/get';
import { CustomerProfileRes } from '../types/api/response';

const DefaultCustomerProfile: CustomerProfileRes = {
  profile: {
    id: -1,
    nickname: 'Not Found',
    phoneNumber: null,
    email: null,
  },
};

export const useCustomerProfile = () => {
  const { data: customerProfile = DefaultCustomerProfile, status } = useQuery({
    queryKey: ['customerProfile'],
    queryFn: () => getCustomerProfile(),
  });

  return { customerProfile, status };
};
