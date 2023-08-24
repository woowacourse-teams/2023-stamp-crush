import { CustomerContainer, Container, EmptyCustomers } from './style';
import Text from '../../../components/Text';
import { useEffect, useState } from 'react';
import SearchBar from '../../../components/SearchBar';
import { useQuery } from '@tanstack/react-query';
import SelectBox from '../../../components/SelectBox';
import { getCustomers } from '../../../api/get';
import { CUSTOMERS_ORDER_OPTIONS, INVALID_CAFE_ID, ROUTER_PATH } from '../../../constants';
import { Customer } from '../../../types';
import { CustomersRes } from '../../../types/api';
import LoadingSpinner from '../../../components/LoadingSpinner';
import Customers from './Customers';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import { useNavigate } from 'react-router-dom';

const CustomerList = () => {
  const navigate = useNavigate();
  const cafeId = useRedirectRegisterPage();
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
          cafeId,
        },
      }),
    onSuccess: (data) => {
      orderCustomer(data.customers);
    },
    enabled: cafeId !== INVALID_CAFE_ID,
  });

  useEffect(() => {
    if (
      localStorage.getItem('admin-login-token') === '' ||
      !localStorage.getItem('admin-login-token')
    )
      navigate(ROUTER_PATH.adminLogin);
    if (status === 'success' && data.customers.length !== 0) {
      orderCustomer(data.customers);
    }
  }, [orderOption]);

  if (status === 'loading') return <LoadingSpinner />;
  if (status === 'error') return <CustomerContainer>Error</CustomerContainer>;

  if (data.customers.length === 0)
    return (
      <CustomerContainer>
        <Text variant="pageTitle">내 고객 목록</Text>
        <EmptyCustomers>
          아직 보유고객이 없어요! <br />
          카페를 방문한 고객에게 스탬프를 적립해 보세요.
        </EmptyCustomers>
      </CustomerContainer>
    );

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
