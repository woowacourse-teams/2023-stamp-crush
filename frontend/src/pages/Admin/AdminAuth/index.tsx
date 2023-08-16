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
    console.log('dd');
    const response = await getAdminOAuthToken({
      params: { resourceServer: 'kakao', code: code },
    });

    localStorage.setItem('admin-login-token', response.accessToken);

    // 카페정보 조회해서 없으면 카페등록페이지, 있으면 고객목록페이지로 가야함.
    navigate(ROUTER_PATH.customerList);
  };

  useEffect(() => {
    getToken();
  }, []);

  return <></>;
};

export default AdminAuth;
