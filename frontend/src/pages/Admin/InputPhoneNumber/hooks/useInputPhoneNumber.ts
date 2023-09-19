import { useState, ChangeEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../../constants';
import useGetCustomerRegisterType from './useGetCustomerRegisterType';
import usePostCustomerLinkData from './usePostCustomerLinkData';
import usePostCustomerPhoneNumber from './usePostCustomerPhoneNumber';

const useInputPhoneNumber = () => {
  const navigate = useNavigate();
  const [phoneNumber, setPhoneNumber] = useState('');
  const { data: customerRegisterType } = useGetCustomerRegisterType(phoneNumber);
  const { mutateAsync: mutatePhoneNumber } = usePostCustomerPhoneNumber();
  const { mutateAsync: mutateLinkData } = usePostCustomerLinkData();

  const changePhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    setPhoneNumber(e.target.value);
  };

  const submitPhoneNumber = async () => {
    if (!customerRegisterType) return;
    if (phoneNumber.length < 13) {
      alert('전화번호를 모두 입력해주세요.');
      return;
    }

    /** 순서가 바뀌면 안되는 로직입니다. */
    // 임시회원이 아닌 유저가 정상적인 전화번호를 등록하려고 할때
    if (customerRegisterType.length === 0) {
      await mutatePhoneNumber(phoneNumber);
      navigate(ROUTER_PATH.couponList);
      return;
    }

    // 임시회원이 자신의 전화번호를 연동하려고 할 때
    if (customerRegisterType[0].registerType === 'temporary') {
      await mutateLinkData(customerRegisterType[0].id);
      localStorage.setItem('login-token', '');
      navigate(ROUTER_PATH.login);
    }

    // 이미 등록되어 있는 전화번호로 등록하려고 할 때
    if (customerRegisterType[0].registerType === 'register')
      alert('이미 등록되어 있는 전화번호입니다. 다시 입력해주세요.');
  };

  return { phoneNumber, changePhoneNumber, submitPhoneNumber };
};

export default useInputPhoneNumber;
