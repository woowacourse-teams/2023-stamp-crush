import { useMutation } from '@tanstack/react-query';
import { postAdminLogin } from '../../../../api/post';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../../constants/routerPath';
import { setAdminAccessToken } from '../../../../api/axios';

const usePostAdminLogin = () => {
  const navigate = useNavigate();

  return useMutation(postAdminLogin, {
    onSuccess: async (res) => {
      const resBody = await res.json();
      // TODO: it's will be remove.
      localStorage.setItem('admin-login-token', resBody.accessToken);
      setAdminAccessToken(resBody.accessToken);

      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      alert('로그인에 실패했습니다.');
    },
  });
};

export default usePostAdminLogin;
