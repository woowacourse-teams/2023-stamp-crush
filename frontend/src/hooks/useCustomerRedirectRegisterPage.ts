import { useNavigate } from 'react-router-dom';
import { useCustomerProfile } from './useCustomerProfile';
import { ROUTER_PATH } from '../constants';
import { useEffect, useState } from 'react';

const useCustomerRedirectRegisterPage = () => {
  const navigate = useNavigate();
  const { customerProfile, status } = useCustomerProfile();
  const [hasPhoneNumber, setHasPhoneNumber] = useState(false);

  useEffect(() => {
    !customerProfile?.profile.phoneNumber ? setHasPhoneNumber(false) : setHasPhoneNumber(true);
  }, [customerProfile]);

  const redirectCustomerWithoutPhoneNumber = () => {
    if (!customerProfile?.profile.phoneNumber && status === 'success') {
      alert('전화번호 등록 후 사용해주세요.');
      navigate(ROUTER_PATH.inputPhoneNumber);
    }
  };

  return { redirectCustomerWithoutPhoneNumber, customerProfile, hasPhoneNumber };
};

export default useCustomerRedirectRegisterPage;
