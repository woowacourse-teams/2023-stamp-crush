import { useQuery } from '@tanstack/react-query';
import { getCustomer } from '../../../../api/get';
import { CustomerPhoneNumberRes } from '../../../../types/api/response';
import { CustomerPhoneNumber } from '../../../../types/domain/customer';
import { removeHyphen } from '../../../../utils';
import { PHONE_NUMBER_LENGTH } from '../../../../constants/magicNumber';

const useGetCustomer = (phoneNumber: string) =>
  useQuery<CustomerPhoneNumberRes, Error, CustomerPhoneNumber[]>(['customer'], {
    queryFn: () => getCustomer({ params: { phoneNumber: removeHyphen(phoneNumber) } }),
    select: (data) => data.customer,
    enabled: phoneNumber.length === PHONE_NUMBER_LENGTH,
  });

export default useGetCustomer;
