import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getOAuthToken } from '../../api/get';

const Auth = () => {
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
  };

  useEffect(() => {
    getToken();
  }, []);

  return <></>;
};

export default Auth;
