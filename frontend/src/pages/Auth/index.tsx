import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getOAuthToken } from '../../api/get';
import { ROUTER_PATH } from '../../constants';
import { useCustomerProfile } from '../../hooks/useCustomerProfile';

const Auth = () => {
  const { customerProfile } = useCustomerProfile();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  if (!code) {
    throw new Error('code가 없습니다.');
  }

  const getToken = async () => {
    const response = await getOAuthToken({
      params: { resourceServer: 'kakao', code },
    });

    localStorage.setItem('login-token', response.accessToken);

    if (customerProfile?.profile.phoneNumber) {
      navigate(ROUTER_PATH.couponList);
      return;
    }
    navigate(ROUTER_PATH.inputPhoneNumber);
  };

  useEffect(() => {
    getToken();
  }, []);

  return <></>;
};

export default Auth;
