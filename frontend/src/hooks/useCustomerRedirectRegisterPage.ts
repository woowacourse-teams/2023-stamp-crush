import { useNavigate } from 'react-router-dom';
import { useCustomerProfile } from './useCustomerProfile';
import { ROUTER_PATH } from '../constants';
import { useEffect } from 'react';

const useCustomerRedirectRegisterPage = () => {
  const navigate = useNavigate();
  const { customerProfile, status } = useCustomerProfile();

  useEffect(() => {
    if (!customerProfile?.profile.phoneNumber && status === 'success') {
      alert('전화번호 등록 후 사용해주세요.');
      navigate(ROUTER_PATH.inputPhoneNumber);
    }
  }, [customerProfile]);

  return { customerProfile };
};

export default useCustomerRedirectRegisterPage;
