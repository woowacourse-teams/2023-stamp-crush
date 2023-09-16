import { useMutation } from '@tanstack/react-query';
import { postRegisterCafe } from '../../../../api/post';
import { useCafeQuery } from '../../../../hooks/useRedirectRegisterPage';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../../constants';

const usePostRegisterCafe = () => {
  const navigate = useNavigate();
  const { refetch: refetchCafe } = useCafeQuery();

  return useMutation(postRegisterCafe, {
    onSuccess: () => {
      refetchCafe();
      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      throw new Error('카페 등록에 실패했습니다.');
    },
  });
};

export default usePostRegisterCafe;
