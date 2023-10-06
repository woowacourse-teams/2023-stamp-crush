import { useQueryClient } from '@tanstack/react-query';
import { useState, useRef, ChangeEvent, KeyboardEvent, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { BACK_KEY_INDEX, ENTER_KEY_INDEX, PHONE_NUMBER_LENGTH } from '../../../../constants';
import { removeHyphen } from '../../../../utils';
import { DialKeyType, DIAL_KEYS } from '../Dialpad';
import useGetCustomer from './useGetCustomer';
import usePostTemporaryCustomer from './usePostTemporaryCustomer';
import REGEX from '../../../../constants/regex';
import ROUTER_PATH from '../../../../constants/routerPath';

const addHyphenToPhoneNumber = (phoneNumber: string) => {
  return phoneNumber.length === 9
    ? phoneNumber.substring(0, 8) + '-' + phoneNumber.substring(8, phoneNumber.length)
    : phoneNumber;
};

const erasePhoneNumberWithHyphen = (phoneNumber: string) => {
  if (phoneNumber.length < 5) return phoneNumber;
  if (phoneNumber.length === 10) return phoneNumber.substring(0, 8);
  return phoneNumber.substring(0, phoneNumber.length - 1);
};

const useDialPad = (openModal: () => void) => {
  const navigate = useNavigate();
  const [isDone, setIsDone] = useState(false);
  const [phoneNumber, setPhoneNumber] = useState<string>('010-');
  const phoneNumberRef = useRef<HTMLInputElement>(null);

  const queryClient = useQueryClient();
  const { data: customer } = useGetCustomer(phoneNumber);
  const { mutateAsync: mutateTemporaryCustomer } = usePostTemporaryCustomer();

  useEffect(() => {
    if (isDone) navigateNextPage();
  }, [customer]);

  const requestTemporaryCustomer = async () => {
    await mutateTemporaryCustomer({ body: { phoneNumber: removeHyphen(phoneNumber) } });
    queryClient.invalidateQueries({ queryKey: ['customer'] });
  };

  const navigateNextPage = () => {
    if (!customer) return;

    if (customer.length === 0) {
      openModal();
      return;
    }

    if (location.pathname === ROUTER_PATH.enterStamp) {
      navigate(ROUTER_PATH.earnStamp, { state: customer[0] });
    }

    if (location.pathname === ROUTER_PATH.enterReward) {
      navigate(ROUTER_PATH.useReward, { state: customer[0] });
    }
  };

  const handlePhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    if (!REGEX.number.test(removeHyphen(e.target.value))) return;
    setPhoneNumber(addHyphenToPhoneNumber(e.target.value));
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (!(e.target instanceof HTMLInputElement)) return;

    if (e.key === 'Enter') {
      sendPhoneNumber();
    }

    if (e.key === 'Backspace') {
      e.preventDefault();
      setPhoneNumber(erasePhoneNumberWithHyphen(e.target.value));
    }
  };

  const sendPhoneNumber = () => {
    if (phoneNumber.length === PHONE_NUMBER_LENGTH) {
      setIsDone(true);
      navigateNextPage();
    }
  };

  const handlePadPressed = (dialKey: DialKeyType) => () => {
    if (phoneNumberRef.current) phoneNumberRef.current.focus();

    if (dialKey === DIAL_KEYS[BACK_KEY_INDEX]) {
      setPhoneNumber((prev) => erasePhoneNumberWithHyphen(prev));
      return;
    }

    if (dialKey === DIAL_KEYS[ENTER_KEY_INDEX]) {
      sendPhoneNumber();
      return;
    }

    if (phoneNumber.length > PHONE_NUMBER_LENGTH - 1) return;

    setPhoneNumber((prev) => addHyphenToPhoneNumber(prev + dialKey));
  };

  return {
    setIsDone,
    phoneNumber,
    phoneNumberRef,
    handlePhoneNumber,
    handleKeyDown,
    handlePadPressed,
    requestTemporaryCustomer,
  };
};

export default useDialPad;
