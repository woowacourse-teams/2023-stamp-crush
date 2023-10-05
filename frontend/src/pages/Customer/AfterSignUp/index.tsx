import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';

const AfterSignUp = () => {
  const navigate = useNavigate();
  const { customerProfile } = useCustomerProfile();

  useEffect(() => {
    setTimeout(() => {
      navigate(ROUTER_PATH.couponList);
    }, 3000);
  }, []);

  return (
    <div>
      {customerProfile?.profile.nickname}님! 스탬프크러쉬에 가입하신 것을 환영합니다! 3초 후에
      서비스로 이동합니다.
    </div>
  );
};

export default AfterSignUp;
