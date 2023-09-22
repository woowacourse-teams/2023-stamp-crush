import { useQuery } from '@tanstack/react-query';
import { getCustomerRegisterType } from '../../../../api/get';
import { PHONE_NUMBER_LENGTH } from '../../../../constants';
import { removeHyphen } from '../../../../utils';

const useGetCustomerRegisterType = (phoneNumber: string) => {
  return useQuery(['customerRegisterType'], {
    queryFn: () => getCustomerRegisterType({ params: { phoneNumber: removeHyphen(phoneNumber) } }),
    select: (data) => data.customers,
    enabled: phoneNumber.length === PHONE_NUMBER_LENGTH,
  });
};

export default useGetCustomerRegisterType;
