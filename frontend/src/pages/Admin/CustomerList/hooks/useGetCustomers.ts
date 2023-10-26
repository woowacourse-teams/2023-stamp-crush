import { useQueries } from '@tanstack/react-query';
import { getCustomers } from '../../../../api/get';
import { CustomersRes } from '../../../../types/api/response';
import { Customer, RegisterType } from '../../../../types/domain/customer';
import { Option } from '../../../../types/utils';
import { INVALID_CAFE_ID } from '../../../../constants/magicNumber';
import { REGISTER_TYPE_OPTION } from '..';

export interface CustomerOrderOption extends Omit<Option, 'key'> {
  key: keyof Customer;
}

const useGetCustomers = (cafeId: number, key: string, orderOption: CustomerOrderOption) => {
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

  const currentKeyIndex = REGISTER_TYPE_OPTION.findIndex((option) => option.key === key);
  const customers = allQueries[currentKeyIndex].data;
  const isError = !allQueries.every((query) => {
    console.log(query);
    return query.isSuccess;
  });

  return { customers, isError };
};

export default useGetCustomers;
