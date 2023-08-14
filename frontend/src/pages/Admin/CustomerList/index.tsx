import { CustomerContainer, Container } from './style';
import Text from '../../../components/Text';
import { useEffect, useState } from 'react';
import SearchBar from '../../../components/SearchBar';
import { useQuery } from '@tanstack/react-query';
import SelectBox from '../../../components/SelectBox';
import { getCustomers } from '../../../api/get';
import { CUSTOMERS_ORDER_OPTIONS } from '../../../constants';
import { Customer } from '../../../types';
import { CustomersRes } from '../../../types/api';
import LoadingSpinner from '../../../components/LoadingSpinner';
import Customers from './Customers';

const CustomerList = () => {
  const [searchWord, setSearchWord] = useState('');
  const [orderOption, setOrderOption] = useState({ key: 'stampCount', value: '스탬프순' });
  const orderCustomer = (customers: Customer[]) => {
    customers.sort((a: Customer, b: Customer) => {
      if (a[orderOption.key as keyof Customer] === b[orderOption.key as keyof Customer]) {
        return a['nickname'] > b['nickname'] ? 1 : -1;
      }
      return a[orderOption.key as keyof Customer] < b[orderOption.key as keyof Customer] ? 1 : -1;
    });
  };

  const { data, status } = useQuery<CustomersRes>({
    queryKey: ['customers'],
    queryFn: () =>
      getCustomers({
        params: {
          cafeId: 1,
        },
      }),
    onSuccess: (data) => {
      orderCustomer(data.customers);
    },
  });

  useEffect(() => {
    if (status === 'success') {
      orderCustomer(data.customers);
    }
  }, [orderOption]);

  if (status === 'loading') return <LoadingSpinner />;
  if (status === 'error') return <CustomerContainer>Error</CustomerContainer>;

  const searchCustomer = () => {
    if (searchWord === '') return;

    // TODO: 추후에 백엔드와 검색 기능 토의 후 수정 예정
  };

  return (
    <CustomerContainer>
      <Text variant="pageTitle">내 고객 목록</Text>
      <Container>
        <SearchBar searchWord={searchWord} setSearchWord={setSearchWord} onClick={searchCustomer} />
        <SelectBox
          options={CUSTOMERS_ORDER_OPTIONS}
          checkedOption={orderOption}
          setCheckedOption={setOrderOption}
        />
      </Container>
      <Customers customersData={data} />
    </CustomerContainer>
  );
};

export default CustomerList;
