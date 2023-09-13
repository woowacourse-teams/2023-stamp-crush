import { useQuery } from '@tanstack/react-query';
import { getCustomer } from '../../../../api/get';
import { CustomerPhoneNumberRes } from '../../../../types/api/response';
import { removeHyphen } from '../../../../utils';

const useGetCustomer = (phoneNumber: string) =>
  useQuery<CustomerPhoneNumberRes>(['customer'], {
    queryFn: () => getCustomer({ params: { phoneNumber: removeHyphen(phoneNumber) } }),
    enabled: phoneNumber.length === 13,
  });

export default useGetCustomer;
