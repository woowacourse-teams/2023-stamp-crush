import { useLocation, useNavigate } from 'react-router-dom';
import Alert from '../../../../components/Alert';
import { PHONE_NUMBER_LENGTH, ROUTER_PATH } from '../../../../constants';
import useModal from '../../../../hooks/useModal';
import useDialPad from '../hooks/useDialPad';
import { BaseInput, Container, KeyContainer, Pad } from './style';

export const DIAL_KEYS = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '←', '0', '입력'] as const;

export type DialKeyType = (typeof DIAL_KEYS)[number];

const Dialpad = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();
  const {
    setIsDone,
    phoneNumber,
    phoneNumberRef,
    handlePhoneNumber,
    handleKeyDown,
    handlePadPressed,
    requestTemporaryCustomer,
  } = useDialPad(openModal);

  const exitPage = () => {
    closeModal();
    navigate(ROUTER_PATH.customerList);
  };

  const retryEnter = () => {
    setIsDone(false);
    closeModal();
  };

  return (
    <Container>
      {location.pathname === ROUTER_PATH.enterStamp
        ? isOpen && (
            <Alert
              text={phoneNumber + '님, 첫 스탬프 적립이 맞으신가요?'}
              rightOption={'네'}
              leftOption={'다시 입력'}
              onClickRight={requestTemporaryCustomer}
              onClickLeft={retryEnter}
            />
          )
        : isOpen && (
            <Alert
              text={phoneNumber + '님은 \n스탬프크러쉬 회원이 아니에요 🥲'}
              rightOption={'나가기'}
              leftOption={'다시 입력'}
              onClickRight={exitPage}
              onClickLeft={retryEnter}
            />
          )}
      <BaseInput
        id="phoneNumber"
        value={phoneNumber}
        ref={phoneNumberRef}
        type="text"
        autoFocus
        minLength={4}
        maxLength={PHONE_NUMBER_LENGTH}
        onChange={handlePhoneNumber}
        onKeyDown={handleKeyDown}
        autoComplete="off"
        inputMode="none"
      />
      <KeyContainer>
        {DIAL_KEYS.map((dialKey) => (
          <Pad key={dialKey} onClick={handlePadPressed(dialKey)}>
            {dialKey}
          </Pad>
        ))}
      </KeyContainer>
    </Container>
  );
};

export default Dialpad;
