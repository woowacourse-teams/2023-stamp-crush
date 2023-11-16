import { PropsWithChildren, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../constants/routerPath';

interface PrivateProviderProps {
  consumer: 'customer' | 'admin';
}

const PrivateProvider = ({ consumer, children }: PrivateProviderProps & PropsWithChildren) => {
  const navigate = useNavigate();
  const routePath = 'customer' === consumer ? ROUTER_PATH.login : ROUTER_PATH.adminLogin;
  const tokenKey = 'customer' === consumer ? 'login-token' : 'admin-login-token';

  useEffect(() => {
    if (localStorage.getItem(tokenKey) === '' || !localStorage.getItem(tokenKey)) {
      navigate(routePath);
    }
    // 토큰이 비었으면 새로 발급을 해라
  }, [navigate]);

  return <>{children}</>;
};

export default PrivateProvider;
