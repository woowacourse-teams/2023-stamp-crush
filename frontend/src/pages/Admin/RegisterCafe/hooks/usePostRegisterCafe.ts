import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postRegisterCafe } from '../../../../api/post';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../../constants';

const usePostRegisterCafe = () => {
  const navigate = useNavigate();

  const queryClient = useQueryClient();

  return useMutation(postRegisterCafe, {
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['cafe'] });
      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      throw new Error('카페 등록에 실패했습니다.');
    },
  });
};

export default usePostRegisterCafe;
