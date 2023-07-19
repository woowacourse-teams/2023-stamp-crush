import useDialPad from '../../hooks/useDialPad';
import Modal from '../Modal';
import StampAndReward from '../ModalContents/StampAndReward';
import { BaseInput, Container, KeyContainer, Pad } from './Dialpad.style';

const DIAL_KEYS = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '←', '0', '적립'] as const;

export type DialKeyType = (typeof DIAL_KEYS)[number];

const Dialpad = () => {
  const {
    phoneNumber,
    phoneNumberRef,
    handlePhoneNumber,
    handleBackspace,
    pressPad,
    isOpen,
    closeModal,
  } = useDialPad();

  return (
    <>
      <Container>
        <BaseInput
          id="phoneNumber"
          value={phoneNumber}
          ref={phoneNumberRef}
          type="text"
          autoFocus
          minLength={4}
          maxLength={13}
          onChange={handlePhoneNumber}
          onKeyDown={handleBackspace}
        />
        <KeyContainer>
          {DIAL_KEYS.map((dialKey) => (
            <Pad key={dialKey} onClick={pressPad(dialKey)}>
              {dialKey}
            </Pad>
          ))}
        </KeyContainer>
      </Container>
      {isOpen && (
        <Modal closeModal={closeModal}>
          <StampAndReward />
        </Modal>
      )}
    </>
  );
};

export default Dialpad;
