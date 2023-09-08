import { PropsWithChildren, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../constants';

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
  }, [navigate]);

  return <>{children}</>;
};

export default PrivateProvider;
