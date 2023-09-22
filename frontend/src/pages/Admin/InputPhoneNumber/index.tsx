import { Input } from '../../../components/Input';
import { Container } from './style';
import { LoginLogo } from '../../../assets';
import Button from '../../../components/Button';
import { ChangeEvent, useEffect, useState } from 'react';
import { parsePhoneNumber } from '../../../utils';
import { useMutation } from '@tanstack/react-query';
import { postCustomerPhoneNumber } from '../../../api/post';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';

const InputPhoneNumber = () => {
  const { customerProfile } = useCustomerProfile();
  const navigate = useNavigate();

  useEffect(() => {
    if (customerProfile?.profile.phoneNumber) navigate(ROUTER_PATH.couponList);
  }, [customerProfile]);

  const [phoneNumber, setPhoneNumber] = useState('');
  const { mutate } = useMutation({
    mutationFn: () => postCustomerPhoneNumber({ body: { phoneNumber } }),
    onSuccess: () => {
      navigate(ROUTER_PATH.couponList);
    },
  });

  const inputPhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    const removeHyphenPhoneNumber = value.replace(/-/g, '');

    setPhoneNumber(removeHyphenPhoneNumber);
  };

  const submitPhoneNumber = () => {
    if (
      (phoneNumber.startsWith('02') && phoneNumber.length !== (11 || 12)) ||
      (!phoneNumber.startsWith('02') && phoneNumber.length !== 11)
    ) {
      alert('전화번호를 정확하게 입력해주세요.');
      return;
    }

    mutate();
  };

  return (
    <Container>
      <LoginLogo width={200} />
      <Input
        id="input-phone-number"
        label={'전화번호'}
        placeholder={'전화번호를 입력해주세요'}
        maxLength={phoneNumber.startsWith('02') ? 12 : 13}
        pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
        value={parsePhoneNumber(phoneNumber)}
        onChange={inputPhoneNumber}
        inputMode="numeric"
        required
      />
      <Button size="large" onClick={submitPhoneNumber}>
        등록
      </Button>
    </Container>
  );
};

export default InputPhoneNumber;
