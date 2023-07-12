import { EARN_KEY_INDEX, REGEX, REMOVE_KEY_INDEX } from '../constants';
import { ChangeEvent, KeyboardEvent, useRef, useState } from 'react';

const addHypen = (phoneNumber: string) => {
  return phoneNumber.length === 8
    ? phoneNumber.substring(0, 8) + '-' + phoneNumber.substring(8, phoneNumber.length)
    : phoneNumber;
};

const useDialPad = () => {
  const [phoneNumber, setPhoneNumber] = useState<string>('010-');
  const phoneNumberRef = useRef<HTMLInputElement>(null);

  const removeNumber = () => {
    if (phoneNumber.length < 5) {
      return;
    }
    if (phoneNumber.length === 9) {
      setPhoneNumber((prev) => prev.substring(0, 7));
      return;
    }
    setPhoneNumber((prev) => prev.substring(0, prev.length - 1));
  };

  const enter = () => {
    // 추후에 모달이 열리게 구현 예정
  };

  const handlePhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    if (!REGEX.number.test(e.target.value.replaceAll('-', ''))) return;

    setPhoneNumber(addHypen(e.target.value));
  };

  const handleBackspace = (e: KeyboardEvent<HTMLInputElement>) => {
    if (!(e.target instanceof HTMLInputElement)) return;

    if (e.target.value.length < 5 && e.key === 'Backspace') {
      e.preventDefault();
      return;
    }

    if (e.target.value.length === 9 && e.key === 'Backspace') {
      setPhoneNumber(e.target.value.substring(0, 8));
      return;
    }
  };

  const pressPad = (dialKey: string, index: number) => () => {
    if (phoneNumberRef.current) phoneNumberRef.current.focus();

    if (index === REMOVE_KEY_INDEX) {
      removeNumber();
      return;
    }
    if (index === EARN_KEY_INDEX) {
      enter();
      return;
    }

    if (phoneNumber.length > 12) return;

    setPhoneNumber((prev) => addHypen(prev + dialKey));
  };

  return { phoneNumber, phoneNumberRef, handlePhoneNumber, handleBackspace, pressPad };
};

export default useDialPad;
