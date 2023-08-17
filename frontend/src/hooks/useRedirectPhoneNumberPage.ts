import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { getCustomerProfile } from '../api/get';

export const useCustomerProfile = () => {
  const { data: customerProfile, status } = useQuery({
    queryKey: ['customerProfile'],
    queryFn: async () => await getCustomerProfile(),
    staleTime: Infinity,
  });

  return { customerProfile, status };
};

export const useRedirectPhoneNumberPage = () => {
  const navigate = useNavigate();
  const { customerProfile, status } = useCustomerProfile();

  if (customerProfile?.profile.phoneNumber === null && status === 'success') {
    alert('전화번호 등록 후 사용해주세요.');
    navigate('/phone-number');
  }

  return customerProfile;
};
