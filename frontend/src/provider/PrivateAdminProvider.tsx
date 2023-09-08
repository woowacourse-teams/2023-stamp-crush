import { PropsWithChildren, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../constants';

const PrivateAdminProvider = ({ children }: PropsWithChildren) => {
  const navigate = useNavigate();

  useEffect(() => {
    if (
      localStorage.getItem('admin-login-token') === '' ||
      !localStorage.getItem('admin-login-token')
    ) {
      navigate(ROUTER_PATH.adminLogin);
    }
  }, [navigate]);

  return <>{children}</>;
};

export default PrivateAdminProvider;
