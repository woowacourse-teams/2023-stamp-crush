import {
  CustomerContainer,
  Container,
  EmptyCustomers,
  TabContainer,
  RegisterTypeTab,
} from './style';
import Text from '../../../components/Text';
import { useState } from 'react';
import SelectBox from '../../../components/SelectBox';
import { CUSTOMERS_ORDER_OPTIONS, REGISTER_TYPE_OPTION } from '../../../constants';
import LoadingSpinner from '../../../components/LoadingSpinner';
import Customers from './Customers';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import useGetCustomers, { CustomerOrderOption } from './hooks/useGetCustomers';
import { Option } from '../../../types/utils';
import { RegisterType } from '../../../types/domain/customer';

const CustomerList = () => {
  const cafeId = useRedirectRegisterPage();
  const [registerType, setRegisterType] = useState<Option>({ key: 'register', value: '회원' });
  const [orderOption, setOrderOption] = useState({
    key: 'stampCount',
    value: '스탬프순',
  });
  const registerTypeKey = registerType.key === 'all' ? undefined : registerType.key;
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
      <Customers registerTypeOption={registerType} customers={customers} />
    </CustomerContainer>
  );
};

export default CustomerList;
