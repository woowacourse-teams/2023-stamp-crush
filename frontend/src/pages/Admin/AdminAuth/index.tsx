import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getAdminOAuthToken } from '../../../api/get';
import { ROUTER_PATH } from '../../../constants';

const AdminAuth = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  if (!code) {
    throw new Error('code가 없습니다.');
  }

  const getToken = async () => {
    const response = await getAdminOAuthToken({
      params: { resourceServer: 'kakao', code: code },
    });

    localStorage.setItem('admin-login-token', response.accessToken);

    navigate(ROUTER_PATH.customerList);
  };

  useEffect(() => {
    getToken();
  }, []);

  return <></>;
};

export default AdminAuth;
