import {
  CustomerContainer,
  Container,
  EmptyCustomers,
  TabContainer,
  RegisterTypeTab,
  CustomerCount,
} from './style';
import Text from '../../../components/Text';
import { useState } from 'react';
import SelectBox from '../../../components/SelectBox';
import LoadingSpinner from '../../../components/LoadingSpinner';
import Customers from './Customers';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import useGetCustomers, { CustomerOrderOption } from './hooks/useGetCustomers';
import { Option } from '../../../types/utils';
import { RegisterType } from '../../../types/domain/customer';

const CUSTOMERS_ORDER_OPTIONS: CustomerOrderOption[] = [
  {
    key: 'stampCount',
    value: '스탬프순',
  },
  {
    key: 'rewardCount',
    value: '리워드순',
  },
  {
    key: 'visitCount',
    value: '방문횟수순',
  },
  { key: 'recentVisitDate', value: '최근방문순' },
];

const REGISTER_TYPE_OPTION: Option[] = [
  { key: 'all', value: '전체' },
  { key: 'register', value: '회원' },
  { key: 'temporary', value: '임시' },
];

const CustomerList = () => {
  const cafeId = useRedirectRegisterPage();
  const [registerType, setRegisterType] = useState<Option>({ key: 'all', value: '전체' });
  const [orderOption, setOrderOption] = useState({
    key: 'stampCount',
    value: '스탬프순',
  });
  const registerTypeKey = registerType.key === 'all' ? null : registerType.key;
  const { data: customers, status } = useGetCustomers(
    cafeId,
    orderOption as CustomerOrderOption,
    registerTypeKey as RegisterType,
  );

  const changeRegisterType = (registerType: Option) => () => {
    setRegisterType(registerType);
  };

  if (status === 'loading') return <LoadingSpinner />;
  if (status === 'error') return <CustomerContainer>Error</CustomerContainer>;

  return (
    <CustomerContainer>
      <Text variant="pageTitle">
        내 고객 목록 <CustomerCount>총 {customers.length}명</CustomerCount>
      </Text>
      <Container>
        <TabContainer>
          {REGISTER_TYPE_OPTION.map((option) => (
            <RegisterTypeTab
              key={option.key}
              $isSelected={registerType.key === option.key}
              onClick={changeRegisterType(option)}
            >
              {option.value}
            </RegisterTypeTab>
          ))}
        </TabContainer>
        <SelectBox
          options={CUSTOMERS_ORDER_OPTIONS}
          checkedOption={orderOption}
          setCheckedOption={setOrderOption}
        />
      </Container>
      {customers.length === 0 ? (
        <EmptyCustomers>
          <span>NO RESULT 🥲</span> 아직 고객이 없어요! <br />
          카페를 방문한 고객에게 스탬프를 적립해 보세요.
        </EmptyCustomers>
      ) : (
        <Customers registerTypeOption={registerType} customers={customers} />
      )}
    </CustomerContainer>
  );
};

export default CustomerList;
