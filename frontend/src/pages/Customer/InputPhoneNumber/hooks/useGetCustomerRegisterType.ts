import { useQuery } from '@tanstack/react-query';
import { getCustomerRegisterType } from '../../../../api/get';
import { removeHyphen } from '../../../../utils';
import { PHONE_NUMBER_LENGTH } from '../../../../constants/magicNumber';

const useGetCustomerRegisterType = (phoneNumber: string) => {
  return useQuery(['customerRegisterType'], {
    queryFn: () => getCustomerRegisterType({ params: { phoneNumber: removeHyphen(phoneNumber) } }),
    select: (data) => data.customers,
    enabled: phoneNumber.length === PHONE_NUMBER_LENGTH,
  });
};

export default useGetCustomerRegisterType;
