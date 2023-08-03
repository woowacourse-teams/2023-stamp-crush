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
import { CustomerRes } from '../../../types/api';

const CustomerList = () => {
  const [searchWord, setSearchWord] = useState('');
  const [orderOption, setOrderOption] = useState({ key: 'stampCount', value: '스탬프순' });
  const orderCustomer = (customers: CustomerRes[]) => {
    customers.sort((a: any, b: any) => (a[orderOption.key] < b[orderOption.key] ? 1 : -1));
  };

  const { data, status } = useQuery(['customers'], getList, {
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    onSuccess: (data) => {
      orderCustomer(data.customers);
    },
  });

  useEffect(() => {
    if (status === 'success') {
      orderCustomer(data.customers);
    }
  }, [orderOption]);

  if (status === 'loading') return <CustomerContainer>Loading</CustomerContainer>;
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
