import { useQuery } from '@tanstack/react-query';
import { getCustomerRegisterType } from '../../../../api/get';

const useGetCustomerRegisterType = (phoneNumber: string) => {
  return useQuery(['customerRegisterType'], {
    queryFn: () => getCustomerRegisterType({ params: { phoneNumber } }),
    select: (data) => data.customers,
    enabled: phoneNumber.length !== 13,
  });
};

export default useGetCustomerRegisterType;
