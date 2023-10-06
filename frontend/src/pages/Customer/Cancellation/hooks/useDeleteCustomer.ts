import { useMutation } from '@tanstack/react-query';
import { deleteCustomer } from '../../../../api/delete';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../../constants/routerPath';

const useDeleteCustomer = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: () => deleteCustomer(),
    onSuccess: () => {
      alert('회원 탈퇴에 성공했습니다.');
      // TODO: 완전한 로그아웃 및 페이지 이동
      localStorage.setItem('login-token', '');
      navigate(ROUTER_PATH.login);
    },
    onError: () => {
      throw new Error('회원 탈퇴에 실패했습니다.');
    },
  });
};

export default useDeleteCustomer;
