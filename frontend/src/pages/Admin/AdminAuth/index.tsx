import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getAdminOAuthToken } from '../../../api/get';

const AdminAuth = () => {
  const [searchParams] = useSearchParams();
  // const code = searchParams.get('code');

  // if (!code) {
  //   throw new Error('code가 없습니다.');
  // }

  const getToken = async () => {
    console.log('dd');
    const response = await getAdminOAuthToken({
      params: { resourceServer: 'kakao', code: 'fff' },
    });

    localStorage.setItem('admin-login-token', response.accessToken);
  };

  useEffect(() => {
    getToken();
  }, []);

  return <></>;
};

export default AdminAuth;
