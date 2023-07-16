import { REGEX } from '../constants';
import { ChangeEvent, KeyboardEvent, useRef, useState } from 'react';
import { DialKeyType } from '../components/Dialpad';
import useModal from './useModal';

const addHypen = (phoneNumber: string) => {
  return phoneNumber.length === 8
    ? phoneNumber.substring(0, 8) + '-' + phoneNumber.substring(8, phoneNumber.length)
    : phoneNumber;
};

const useDialPad = () => {
  const [phoneNumber, setPhoneNumber] = useState<string>('010-');
  const phoneNumberRef = useRef<HTMLInputElement>(null);

  const { openModal, closeModal, isOpen } = useModal();

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
    openModal();
  };

  const handlePhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.value.length > 4 && e.target.value.endsWith('-'))
      e.target.value = e.target.value.substring(0, e.target.value.length - 1);

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

  const pressPad = (dialKey: DialKeyType) => () => {
    if (phoneNumberRef.current) phoneNumberRef.current.focus();

    if (dialKey === '←') {
      removeNumber();
      return;
    }
    if (dialKey === '적립') {
      enter();
      return;
    }

    if (phoneNumber.length > 12) return;

    setPhoneNumber((prev) => addHypen(prev + dialKey));
  };

  return {
    phoneNumber,
    phoneNumberRef,
    handlePhoneNumber,
    handleBackspace,
    pressPad,
    isOpen,
    closeModal,
  };
};

export default useDialPad;
