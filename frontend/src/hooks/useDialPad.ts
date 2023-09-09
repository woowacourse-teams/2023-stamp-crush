import { PHONE_NUMBER_LENGTH, REGEX, ROUTER_PATH } from '../constants';
import { ChangeEvent, KeyboardEvent, useRef, useState } from 'react';
import { DialKeyType } from '../pages/Admin/EnterPhoneNumber/Dialpad';
import { useLocation, useNavigate } from 'react-router-dom';
import { CustomerPhoneNumber } from '../types';

const addHypen = (phoneNumber: string) => {
  return phoneNumber.length === 8
    ? phoneNumber.substring(0, 8) + '-' + phoneNumber.substring(8, phoneNumber.length)
    : phoneNumber;
};

const useDialPad = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [isDone, setIsDone] = useState(false);
  const [phoneNumber, setPhoneNumber] = useState<string>('010-');
  const phoneNumberRef = useRef<HTMLInputElement>(null);

  const removeNumber = () => {
    if (phoneNumber.length < 5) return;

    if (phoneNumber.length === 9) {
      setPhoneNumber((prev) => prev.substring(0, 7));
      return;
    }

    setPhoneNumber((prev) => prev.substring(0, prev.length - 1));
  };

  const navigateNextPage = (customerState: CustomerPhoneNumber) => {
    if (phoneNumber.length !== PHONE_NUMBER_LENGTH) {
      alert('올바른 전화번호를 입력해주세요.');
      return;
    }

    if (location.pathname === ROUTER_PATH.enterStamp) {
      navigate(ROUTER_PATH.selectCoupon, { state: customerState });
    }

    if (location.pathname === ROUTER_PATH.enterReward) {
      navigate(ROUTER_PATH.useReward, { state: customerState });
    }
  };

  const handlePhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.value.length > 4 && e.target.value.endsWith('-'))
      e.target.value = e.target.value.substring(0, e.target.value.length - 1);

    if (!REGEX.number.test(e.target.value.replaceAll('-', ''))) return;

    setPhoneNumber(addHypen(e.target.value));
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (!(e.target instanceof HTMLInputElement)) return;

    if (e.target.value.length < 5 && e.key === 'Backspace') {
      e.preventDefault();
      return;
    }

    if (e.target.value.length === 9 && e.key === 'Backspace') {
      setPhoneNumber(e.target.value.substring(0, 8));
      return;
    }

    if (e.target.value.length === 13 && e.key === 'Enter') {
      pressPad('입력')();
      return;
    }
  };

  const pressPad = (dialKey: DialKeyType) => () => {
    if (phoneNumberRef.current) phoneNumberRef.current.focus();

    if (dialKey === '←') {
      removeNumber();
      return;
    }

    if (phoneNumber.length === 13 && dialKey === '입력') {
      setIsDone(true);
      return;
    }

    if (phoneNumber.length > 12 || dialKey === '입력') return;

    setPhoneNumber((prev) => addHypen(prev + dialKey));
  };

  return {
    isDone,
    setIsDone,
    phoneNumber,
    phoneNumberRef,
    handlePhoneNumber,
    handleKeyDown,
    pressPad,
    navigateNextPage,
  };
};

export default useDialPad;
