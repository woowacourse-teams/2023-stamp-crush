import { ChangeEvent, KeyboardEvent, useRef, useState } from 'react';

const REMOVE_KEY_INDEX = 9;
const EARN_KEY_INDEX = 11;
const REGEX = {
  number: /^[0-9]+$/,
} as const;

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
    // 모달 열리게
  };

  const handlePhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    if (!REGEX.number.test(e.target.value.replaceAll('-', ''))) return;

    setPhoneNumber(addHypen(e.target.value));
  };

  const handleBackspace = (e: KeyboardEvent<HTMLInputElement>) => {
    if (!(e.target instanceof HTMLInputElement)) return;

    // 010-만 있을 때 막는 로직
    if (e.target.value.length < 5 && e.key === 'Backspace') {
      e.preventDefault();
      return;
    }

    // 두번째 하이픈에서 지우는 로직
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
