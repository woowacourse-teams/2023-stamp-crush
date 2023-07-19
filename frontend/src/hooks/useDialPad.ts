import { PHONE_NUMBER_LENGTH, REGEX } from '../constants';
import { ChangeEvent, KeyboardEvent, useRef, useState } from 'react';
import { DialKeyType } from '../components/Dialpad';
import { useLocation, useNavigate } from 'react-router-dom';

const addHypen = (phoneNumber: string) => {
  return phoneNumber.length === 8
    ? phoneNumber.substring(0, 8) + '-' + phoneNumber.substring(8, phoneNumber.length)
    : phoneNumber;
};

const useDialPad = () => {
  const [phoneNumber, setPhoneNumber] = useState<string>('010-');
  const phoneNumberRef = useRef<HTMLInputElement>(null);
  const navigate = useNavigate();
  const location = useLocation();

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
    if (phoneNumber.length !== PHONE_NUMBER_LENGTH) {
      alert('올바른 전화번호를 입력해주세요.');
      return;
    }
    if (location.pathname === '/admin/stamp') {
      navigate('/admin/stamp/1', { state: { phoneNumber } });
      return;
    }

    if (location.pathname === '/admin/enter-reward') {
      navigate('/admin/reward/input-reward', { state: { phoneNumber } });
      return;
    }
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
    if (dialKey === '입력') {
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
  };
};

export default useDialPad;
