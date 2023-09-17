import { useQuery } from '@tanstack/react-query';
import { getCustomers } from '../../../../api/get';
import { INVALID_CAFE_ID } from '../../../../constants';
import { CustomersRes } from '../../../../types/api/response';
import { Customer } from '../../../../types/domain/customer';
import { Option } from '../../../../types/utils';

const useGetCustomers = (cafeId: number, orderOption: Option) => {
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
        if (a[orderOption.key as keyof Customer] === b[orderOption.key as keyof Customer])
          return a['nickname'] > b['nickname'] ? 1 : -1;
        return a[orderOption.key as keyof Customer] < b[orderOption.key as keyof Customer] ? 1 : -1;
      }),
    enabled: cafeId !== INVALID_CAFE_ID,
  });
};

export default useGetCustomers;
