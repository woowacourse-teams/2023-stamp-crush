import { CustomerContainer, Container, EmptyCustomers } from './style';
import Text from '../../../components/Text';
import { useState } from 'react';
import SelectBox from '../../../components/SelectBox';
import { CUSTOMERS_ORDER_OPTIONS } from '../../../constants';
import LoadingSpinner from '../../../components/LoadingSpinner';
import Customers from './Customers';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import useGetCustomers from './hooks/useGetCustomers';

const CustomerList = () => {
  const cafeId = useRedirectRegisterPage();
  const [orderOption, setOrderOption] = useState({
    key: 'stampCount',
    value: '스탬프순',
  });
  const { data: customers, status } = useGetCustomers(cafeId, orderOption);

  if (status === 'loading') return <LoadingSpinner />;
  if (status === 'error') return <CustomerContainer>Error</CustomerContainer>;

  if (customers.length === 0)
    return (
      <CustomerContainer>
        <Text variant="pageTitle">내 고객 목록</Text>
        <EmptyCustomers>
          아직 보유고객이 없어요! <br />
          카페를 방문한 고객에게 스탬프를 적립해 보세요.
        </EmptyCustomers>
      </CustomerContainer>
    );

  return (
    <CustomerContainer>
      <Text variant="pageTitle">내 고객 목록</Text>
      <Container>
        <SelectBox
          options={CUSTOMERS_ORDER_OPTIONS}
          checkedOption={orderOption}
          setCheckedOption={setOrderOption}
        />
      </Container>
      <Customers customers={customers} />
    </CustomerContainer>
  );
};

export default CustomerList;
