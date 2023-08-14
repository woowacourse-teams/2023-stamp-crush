import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { api, ownerHeader } from '../../../api';

const AdminAuth = () => {
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');
  const [location, setLocation] = useState('');

  const getToken = async () => {
    const response: any = await api.get(
      `/admin/login/kakao/token?authorization-code=${code}`,
      ownerHeader,
    );

    const location = response.headers.get('Location');
    if (location === null) {
      throw new Error('Location이 없습니다.');
    }
    setLocation(location);
  };

  useEffect(() => {
    if (!code) {
      throw new Error('code가 없습니다.');
    }

    getToken();
  }, []);

  return <></>;
};

export default AdminAuth;
