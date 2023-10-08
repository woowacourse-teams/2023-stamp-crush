import { useMutation } from '@tanstack/react-query';
import { postAdminLogin } from '../../../../api/post';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../../constants/routerPath';

const usePostAdminLogin = () => {
  const navigate = useNavigate();

  return useMutation(postAdminLogin, {
    onSuccess: async (res) => {
      await res.json().then((data) => {
        localStorage.setItem('admin-login-token', data.accessToken);
      });

      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      alert('로그인에 실패했습니다.');
    },
  });
};

export default usePostAdminLogin;
