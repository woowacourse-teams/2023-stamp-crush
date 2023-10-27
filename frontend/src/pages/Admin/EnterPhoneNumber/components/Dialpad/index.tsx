import { useNavigate } from 'react-router-dom';
import useModal from '../../../../../hooks/useModal';
import useDialPad from '../../hooks/useDialPad';
import { BaseInput, Container, KeyContainer, Pad } from './style';
import ROUTER_PATH from '../../../../../constants/routerPath';
import { PHONE_NUMBER_LENGTH } from '../../../../../constants/magicNumber';
import GuideAlert from '../GuideAlert';

export const DIAL_KEYS = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '←', '0', '입력'] as const;

export type DialKeyType = (typeof DIAL_KEYS)[number];

const Dialpad = () => {
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();
  const {
    phoneNumber,
    phoneNumberRef,
    customerStatus,
    postTemporaryCustomerStatus,
    setIsDone,
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
      <GuideAlert
        isOpen={isOpen}
        phoneNumber={phoneNumber}
        customerStatus={customerStatus}
        postTemporaryCustomerStatus={postTemporaryCustomerStatus}
        enterStampHandler={requestTemporaryCustomer}
        enterRewardHandler={exitPage}
        retryEnter={retryEnter}
      />
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
