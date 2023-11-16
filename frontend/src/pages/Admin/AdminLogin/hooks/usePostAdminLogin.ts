import { useMutation } from '@tanstack/react-query';
import { postAdminLogin } from '../../../../api/post';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../../constants/routerPath';
import { setAdminAccessToken } from '../../../../api/axios';

const usePostAdminLogin = () => {
  const navigate = useNavigate();

  return useMutation(postAdminLogin, {
    onSuccess: (res) => {
      const resBody = res.data;
      // TODO: it's will be remove.
      // localStorage.setItem('admin-login-token', resBody.accessToken);

      // 최초 로그인시 set token 로직
      setAdminAccessToken(resBody.accessToken);

      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      alert('로그인에 실패했습니다.');
    },
  });
};

export default usePostAdminLogin;
