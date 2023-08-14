import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { api, ownerHeader } from '../../../api';
import { getOAuthToken } from '../../../api/get';

const AdminAuth = () => {
  const [searchParams] = useSearchParams();
  const authorizationCode = searchParams.get('authorization-code');

  if (!authorizationCode) {
    throw new Error('code가 없습니다.');
  }

  const getToken = async () => {
    const response = await getOAuthToken({
      params: { resourceServer: 'kakao', authorizationCode },
    });

    localStorage.setItem('login-token', response.accessToken);
  };

  useEffect(() => {
    getToken();
  }, []);

  return <></>;
};

export default AdminAuth;
