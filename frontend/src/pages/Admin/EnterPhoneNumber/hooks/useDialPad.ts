import { useQueryClient } from '@tanstack/react-query';
import { useState, useRef, ChangeEvent, KeyboardEvent, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { REGEX, ROUTER_PATH } from '../../../../constants';
import { removeHyphen } from '../../../../utils';
import { DialKeyType } from '../Dialpad';
import useGetCustomer from './useGetCustomer';
import usePostTemporaryCustomer from './usePostTemporaryCustomer';

const addHyphen = (phoneNumber: string) => {
  return phoneNumber.length === 9
    ? phoneNumber.substring(0, 8) + '-' + phoneNumber.substring(8, phoneNumber.length)
    : phoneNumber;
};

const removeNumber = (phoneNumber: string) => {
  if (phoneNumber.length < 5) return phoneNumber;
  if (phoneNumber.length === 10) return phoneNumber.substring(0, 8);
  return phoneNumber.substring(0, phoneNumber.length - 1);
};

const useDialPad = (openModal: () => void) => {
  const navigate = useNavigate();
  const [phoneNumber, setPhoneNumber] = useState<string>('010-');
  const phoneNumberRef = useRef<HTMLInputElement>(null);

  const queryClient = useQueryClient();
  const { data: customer } = useGetCustomer(phoneNumber);
  const { mutateAsync: mutateTemporaryCustomer } = usePostTemporaryCustomer();

  useEffect(() => {
    navigateNextPage();
  }, [customer]);

  const requestTemporaryCustomer = async () => {
    await mutateTemporaryCustomer({ body: { phoneNumber: removeHyphen(phoneNumber) } });
    queryClient.invalidateQueries({ queryKey: ['customer'] });
  };

  const navigateNextPage = () => {
    if (!customer) return;

    if (customer.customer.length === 0) {
      openModal();
      return;
    }

    if (location.pathname === ROUTER_PATH.enterStamp) {
      navigate(ROUTER_PATH.earnStamp, { state: customer.customer[0] });
    }

    if (location.pathname === ROUTER_PATH.enterReward) {
      navigate(ROUTER_PATH.useReward, { state: customer.customer[0] });
    }
  };

  const handlePhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    if (!REGEX.number.test(removeHyphen(e.target.value))) return;
    setPhoneNumber(addHyphen(e.target.value));
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (!(e.target instanceof HTMLInputElement)) return;

    if (e.key === 'Enter') {
      sendPhoneNumber();
    }

    if (e.key === 'Backspace') {
      e.preventDefault();
      setPhoneNumber(removeNumber(e.target.value));
    }
  };

  const sendPhoneNumber = () => {
    if (phoneNumber.length === 13) {
      navigateNextPage();
    }
  };

  const handlePadPressed = (dialKey: DialKeyType) => () => {
    if (phoneNumberRef.current) phoneNumberRef.current.focus();
    if (phoneNumber.length > 12) return;

    if (dialKey === '←') {
      setPhoneNumber((prev) => removeNumber(prev));
      return;
    }

    if (dialKey === '입력') {
      sendPhoneNumber();
      return;
    }

    setPhoneNumber((prev) => addHyphen(prev + dialKey));
  };

  return {
    phoneNumber,
    phoneNumberRef,
    handlePhoneNumber,
    handleKeyDown,
    handlePadPressed,
    requestTemporaryCustomer,
  };
};

export default useDialPad;
