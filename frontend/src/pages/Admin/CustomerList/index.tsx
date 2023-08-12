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
  EmptyCustomers,
  CustomerBoxContainer,
} from './style';
import Text from '../../../components/Text';
import { useEffect, useState } from 'react';
import SearchBar from '../../../components/SearchBar';
import { useQuery } from '@tanstack/react-query';
import SelectBox from '../../../components/SelectBox';
import { getCustomers } from '../../../api/get';
import { CUSTOMERS_ORDER_OPTIONS } from '../../../constants';
import { Customer } from '../../../types';
import { CustomersRes } from '../../../types/api';

const CustomerList = () => {
  const [searchWord, setSearchWord] = useState('');
  const [orderOption, setOrderOption] = useState({ key: 'stampCount', value: '스탬프순' });
  const orderCustomer = (customers: Customer[]) => {
    customers.sort((a: any, b: any) => (a[orderOption.key] < b[orderOption.key] ? 1 : -1));
  };

  const { data, status } = useQuery<CustomersRes>(
    ['customers'],
    () =>
      getCustomers({
        params: {
          cafeId: 1,
        },
      }),
    {
      refetchOnMount: false,
      refetchOnWindowFocus: false,
      onSuccess: (data) => {
        orderCustomer(data.customers);
      },
    },
  );

  useEffect(() => {
    if (status === 'success' && data.customers.length !== 0) {
      orderCustomer(data.customers);
    }
  }, [orderOption]);

  if (status === 'loading') return <CustomerContainer>Loading</CustomerContainer>;
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
      <CustomerBoxContainer>
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
          }: Customer) => (
            <CustomerBox key={id}>
              <LeftInfo>
                <NameContainer>
                  <Name>{nickname}</Name>
                  <Badge $isRegistered={isRegistered}>{isRegistered ? '회원' : '임시'}</Badge>
                </NameContainer>
                <InfoContainer>
                  보유 스탬프: {stampCount} / {maxStampCount} <br />
                  보유 리워드: {rewardCount}(개)
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
      </CustomerBoxContainer>
    </CustomerContainer>
  );
};

export default CustomerList;
