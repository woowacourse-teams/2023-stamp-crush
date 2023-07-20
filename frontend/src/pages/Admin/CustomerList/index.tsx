import {
  Badge,
  CustomerBox,
  CustomerContainer,
  LeftInfo,
  Name,
  RightInfo,
  Container,
} from './style';
import Text from '../../../components/Text';
import { useEffect, useState } from 'react';
import SearchBar from '../../../components/SearchBar';
import { useQuery } from '@tanstack/react-query';
import SelectBox from '../../../components/SelectBox';
import { BASE_URL } from '../../..';

const CUSTOMERS_ORDER_OPTIONS = [
  {
    key: 'stampCount',
    value: '스탬프순',
  },
  {
    key: 'rewardCount',
    value: '리워드순',
  },
  {
    key: 'firstVisitDate',
    value: '방문일순',
  },
  {
    key: 'visitCount',
    value: '방문횟수순',
  },
];

interface CustomerType {
  id: number;
  nickname: string;
  stampCount: number;
  maxStampCount: number;
  rewardCount: number;
  visitCount: number;
  firstVisitDate: string;
  isRegistered: boolean;
}

const getList = async () => {
  const data = await fetch(`${BASE_URL}/cafes/1/customers`);
  return await data.json();
};

const CustomerList = () => {
  const [searchWord, setSearchWord] = useState('');
  const [orderOption, setOrderOption] = useState({ key: 'stampCount', value: '스탬프순' });
  const [customers, setCustomers] = useState<CustomerType[]>([]);
  const { isLoading, isError, data, isSuccess, refetch:foo } = useQuery(['customers'], getList, {
    onSuccess: (data) => {
      setCustomers(data.customers);
      orderCustomer();
    },
    refetchOnMount: 'always'
  });

  const orderCustomer = () => {
    if (!isSuccess) return;
    const customers = data.customers;

    switch (orderOption.key) {
      case 'firstVisitDate':
        // TODO: 방문일 순 로직 구현
        break;
      default:
        customers.sort((a: any, b: any) => (a[orderOption.key] < b[orderOption.key] ? 1 : -1));
        setCustomers([...customers]);
        break;
    }
  };

  const searchCustomer = () => {
    if (searchWord === '') return;
    setCustomers([
      ...data.customers.filter(
        (customer: CustomerType) => customer.nickname === searchWord && customer,
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
      {data.customers.map(
        ({
          id,
          nickname,
          stampCount,
          maxStampCount,
          rewardCount,
          isRegistered,
          firstVisitDate,
          visitCount,
        }: CustomerType) => (
          <CustomerBox key={id}>
            <LeftInfo>
              <Name>
                <h1>{nickname}</h1>
                <Badge $isRegistered={isRegistered}>{isRegistered ? '회원' : '임시'}</Badge>
              </Name>
              <span>
                스탬프: {stampCount}/{maxStampCount} <br />
                리워드: {rewardCount}개
              </span>
            </LeftInfo>
            <RightInfo>
              <span>첫 방문일: {firstVisitDate}</span>
              <span>방문 횟수: {visitCount}번</span>
            </RightInfo>
          </CustomerBox>
        ),
      )}
    </CustomerContainer>
  );
};

export default CustomerList;
