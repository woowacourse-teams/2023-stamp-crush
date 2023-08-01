import {
  Badge,
  CustomerBox,
  CustomerContainer,
  LeftInfo,
  Name,
  RightInfo,
  Container,
  NameContainer,
  InfoContainer,
} from './style';
import Text from '../../../components/Text';
import { useEffect, useState } from 'react';
import SearchBar from '../../../components/SearchBar';
import { useQuery } from '@tanstack/react-query';
import SelectBox from '../../../components/SelectBox';
import { getList } from '../../../api/get';
import { CUSTOMERS_ORDER_OPTIONS } from '../../../constants';
import { CustomerRes } from '../../../types';

const CustomerList = () => {
  const [searchWord, setSearchWord] = useState('');
  const [orderOption, setOrderOption] = useState({ key: 'stampCount', value: '스탬프순' });
  const [customers, setCustomers] = useState<CustomerRes[]>([]);
  const { isLoading, isError, data, isSuccess } = useQuery(['customers'], getList, {
    onSuccess: (data) => {
      setCustomers(data.customers);
      orderCustomer();
    },
  });

  const orderCustomer = () => {
    if (!isSuccess) return;
    const customers = data.customers;

    switch (orderOption.key) {
      case 'firstVisitDate':
        // TODO: 방문일 순 로직 구현
        break;
      default:
        // TODO: any 제거
        customers.sort((a: any, b: any) => (a[orderOption.key] < b[orderOption.key] ? 1 : -1));
        setCustomers([...customers]);
        break;
    }
  };

  const searchCustomer = () => {
    if (searchWord === '') return;
    setCustomers([
      ...data.customers.filter(
        (customer: CustomerRes) => customer.nickname === searchWord && customer,
      ),
    ]);
  };

  useEffect(() => {
    if (isSuccess) {
      orderCustomer();
    }
  }, [orderOption]);

  if (isLoading) return <CustomerContainer>Loading</CustomerContainer>;
  if (isError) return <CustomerContainer>Error</CustomerContainer>;

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
      {customers.map(
        ({
          id,
          nickname,
          stampCount,
          maxStampCount,
          rewardCount,
          isRegistered,
          firstVisitDate,
          visitCount,
        }: CustomerRes) => (
          <CustomerBox key={id}>
            <LeftInfo>
              <NameContainer>
                <Name>{nickname}</Name>
                <Badge $isRegistered={isRegistered}>{isRegistered ? '회원' : '임시'}</Badge>
              </NameContainer>
              <InfoContainer>
                스탬프: {stampCount}/{maxStampCount} <br />
                리워드: {rewardCount}개
              </InfoContainer>
            </LeftInfo>
            <RightInfo>
              <InfoContainer>
                첫 방문일: {firstVisitDate}
                <br /> 방문 횟수: {visitCount}번
              </InfoContainer>
            </RightInfo>
          </CustomerBox>
        ),
      )}
    </CustomerContainer>
  );
};

export default CustomerList;
