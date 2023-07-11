import useDialPad from '../../hooks/useDialPad';
import { BaseInput, Container, KeyContainer, Pad } from './Dialpad.style';

const DIAL_KEYS = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '←', '0', '적립'];

const Dialpad = () => {
  const { phoneNumber, phoneNumberRef, handlePhoneNumber, handleBackspace, pressPad } =
    useDialPad();

  return (
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
        {DIAL_KEYS.map((dialKey, index) => (
          <Pad key={crypto.randomUUID()} onClick={pressPad(dialKey, index)}>
            {dialKey}
          </Pad>
        ))}
      </KeyContainer>
    </Container>
  );
};

export default Dialpad;
