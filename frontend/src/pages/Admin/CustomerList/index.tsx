import {
  CustomerContainer,
  Container,
  TabContainer,
  RegisterTypeTab,
  CustomerCount,
} from './style';
import Text from '../../../components/Text';
import { useState } from 'react';
import SelectBox from '../../../components/SelectBox';
import Customers from './components/Customers';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import useGetCustomers, { CustomerOrderOption } from './hooks/useGetCustomers';
import { Option } from '../../../types/utils';
import { RegisterType } from '../../../types/domain/customer';

const CUSTOMERS_ORDER_OPTIONS: CustomerOrderOption[] = [
  { key: 'recentVisitDate', value: '최근방문순' },
  {
    key: 'visitCount',
    value: '방문횟수순',
  },
  {
    key: 'stampCount',
    value: '스탬프순',
  },
  {
    key: 'rewardCount',
    value: '리워드순',
  },
];

const REGISTER_TYPE_OPTION: Option[] = [
  { key: 'all', value: '전체' },
  { key: 'register', value: '회원' },
  { key: 'temporary', value: '임시' },
];

const CustomerList = () => {
  const cafeId = useRedirectRegisterPage();
  const [registerType, setRegisterType] = useState<Option>({ key: 'all', value: '전체' });
  const [orderOption, setOrderOption] = useState({ key: 'recentVisitDate', value: '최근방문순' });
  const registerTypeKey = registerType.key === 'all' ? null : registerType.key;
  const { data: customers, status: customersStatus } = useGetCustomers(
    cafeId,
    orderOption as CustomerOrderOption,
    registerTypeKey as RegisterType,
  );
  const customersCount = customers ? customers.length : 0;

  const changeRegisterType = (registerType: Option) => () => {
    setRegisterType(registerType);
  };

  return (
    <CustomerContainer>
      <Text variant="pageTitle">
        내 고객 목록 <CustomerCount>총 {customersCount}명</CustomerCount>
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
      <Customers customers={customers} customersStatus={customersStatus} />
    </CustomerContainer>
  );
};

export default CustomerList;
