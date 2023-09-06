import { useQuery } from '@tanstack/react-query';
import { useState, KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCustomer } from '../../../../api/get';
import Alert from '../../../../components/Alert';
import { ROUTER_PATH } from '../../../../constants';
import useDialPad from '../../../../hooks/useDialPad';
import useModal from '../../../../hooks/useModal';
import { CustomerPhoneNumberRes } from '../../../../types/api';
import { BaseInput, Container, KeyContainer, Pad } from './style';

const DIAL_KEYS = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '←', '0', '입력'] as const;

export type DialKeyType = (typeof DIAL_KEYS)[number];

const Dialpad = () => {
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();
  const [isDone, setIsDone] = useState(false);
  const { phoneNumber, phoneNumberRef, handlePhoneNumber, handleBackspace, pressPad } =
    useDialPad();

  const { status: customerStatus } = useQuery<CustomerPhoneNumberRes>(['customer', phoneNumber], {
    queryFn: () => getCustomer({ params: { phoneNumber: phoneNumber.replaceAll('-', '') } }),
    onSuccess: (data) => {
      if (data.customer.length === 0) {
        openModal();
        return;
      }
      navigate(ROUTER_PATH.selectCoupon, {
        state: { phoneNumber: phoneNumber.replaceAll('-', '') },
      });
    },
    enabled: isDone,
  });

  if (customerStatus === 'error') return <div>Error</div>;

  const sendPhoneNumber = (dialKey: DialKeyType) => () => {
    if (phoneNumber.replaceAll('-', '').length === 11 && dialKey === '입력') {
      setIsDone(true);
      return;
    }

    pressPad(dialKey)();
  };

  const retryPhoneNumber = () => {
    closeModal();
    setIsDone(false);
  };

  const handleEnter = (e: KeyboardEvent<HTMLInputElement>) => {
    if (phoneNumber.replaceAll('-', '').length === 11 && e.key === 'Enter') {
      sendPhoneNumber('입력')();
      return;
    }
    handleBackspace(e);
  };

  return (
    <Container>
      {isOpen && (
        <Alert
          text={phoneNumber + '님, 첫 적립이 맞으신가요?'}
          rightOption={'네'}
          leftOption={'다시 입력'}
          onClickRight={pressPad('입력')}
          onClickLeft={retryPhoneNumber}
        />
      )}
      <BaseInput
        id="phoneNumber"
        value={phoneNumber}
        ref={phoneNumberRef}
        type="text"
        autoFocus
        minLength={4}
        maxLength={13}
        onChange={handlePhoneNumber}
        onKeyDown={handleEnter}
        autoComplete="off"
        inputMode="none"
      />
      <KeyContainer>
        {DIAL_KEYS.map((dialKey) => (
          <Pad key={dialKey} onClick={sendPhoneNumber(dialKey)}>
            {dialKey}
          </Pad>
        ))}
      </KeyContainer>
    </Container>
  );
};

export default Dialpad;
