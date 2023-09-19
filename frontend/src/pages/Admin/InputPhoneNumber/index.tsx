import { Input } from '../../../components/Input';
import { LoginLogo } from '../../../assets';
import Button from '../../../components/Button';
import { useEffect } from 'react';
import { parsePhoneNumber } from '../../../utils';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';
import useInputPhoneNumber from './hooks/useInputPhoneNumber';
import { Form } from './style';

const InputPhoneNumber = () => {
  const navigate = useNavigate();
  const { customerProfile } = useCustomerProfile();
  const { phoneNumber, changePhoneNumber, submitPhoneNumber } = useInputPhoneNumber();

  useEffect(() => {
    if (customerProfile?.profile.phoneNumber) navigate(ROUTER_PATH.couponList);
  }, [customerProfile]);

  return (
    <Form onSubmit={submitPhoneNumber}>
      <img src={LoginLogo} alt="스탬프크러쉬 로고" width={200} />
      <Input
        id="input-phone-number"
        label={'전화번호'}
        placeholder={'스탬프 적립시 사용한 전화번호를 입력해주세요'}
        maxLength={13}
        pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
        value={parsePhoneNumber(phoneNumber)}
        onChange={changePhoneNumber}
        inputMode="numeric"
        required
      />
      <Button type="submit" size="large">
        등록
      </Button>
    </Form>
  );
};

export default InputPhoneNumber;
