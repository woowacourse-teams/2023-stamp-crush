import { useQuery } from '@tanstack/react-query';
import { getCustomers } from '../../../../api/get';
import { INVALID_CAFE_ID } from '../../../../constants';
import { CustomersRes } from '../../../../types/api/response';
import { Customer } from '../../../../types/domain/customer';
import { Option } from '../../../../types/utils';

interface CustomerOrderOption extends Omit<Option, 'key'> {
  key: keyof Customer;
}

const useGetCustomers = (cafeId: number, orderOption: CustomerOrderOption) => {
  return useQuery<CustomersRes, Error, Customer[]>({
    queryKey: ['customers'],
    queryFn: () =>
      getCustomers({
        params: {
          cafeId,
        },
      }),
    select: (data) =>
      data.customers.sort((a, b) => {
        if (a[orderOption.key] === b[orderOption.key])
          return a['nickname'] > b['nickname'] ? 1 : -1;
        return a[orderOption.key] < b[orderOption.key] ? 1 : -1;
      }),
    enabled: cafeId !== INVALID_CAFE_ID,
  });
};

export default useGetCustomers;
