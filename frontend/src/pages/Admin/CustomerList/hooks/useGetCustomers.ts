import { useQueries, useQuery } from '@tanstack/react-query';
import { getCustomers } from '../../../../api/get';
import { CustomersRes } from '../../../../types/api/response';
import { Customer, RegisterType } from '../../../../types/domain/customer';
import { Option } from '../../../../types/utils';
import { INVALID_CAFE_ID } from '../../../../constants/magicNumber';
import { REGISTER_TYPE_OPTION } from '..';

export interface CustomerOrderOption extends Omit<Option, 'key'> {
  key: keyof Customer;
}

const useGetCustomers = (cafeId: number, orderOption: CustomerOrderOption) => {
  const allQueries = useQueries({
    queries: REGISTER_TYPE_OPTION.map(({ key }) => {
      const customerType = key === 'all' ? null : key;
      return {
        queryKey: ['customers', customerType],
        queryFn: () =>
          getCustomers({
            params: {
              cafeId,
              customerType: customerType as RegisterType,
            },
          }),
        select: (data: CustomersRes) =>
          data.customers.sort((a, b) => {
            if (a[orderOption.key] === b[orderOption.key])
              return a['nickname'] > b['nickname'] ? 1 : -1;
            return a[orderOption.key] < b[orderOption.key] ? 1 : -1;
          }),
        enabled: cafeId !== INVALID_CAFE_ID,
      };
    }),
  });

  const isError = !allQueries.every((query) => query.isSuccess);

  return { isError };
};

export default useGetCustomers;
