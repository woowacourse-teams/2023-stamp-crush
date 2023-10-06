import { useMutation } from '@tanstack/react-query';
import { postEarnStamp } from '../../../../api/post';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../../constants/routerPath';

const usePostEarnStamp = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: postEarnStamp,
    onSuccess: () => {
      alert('스탬프 적립에 성공했습니다.');
      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      throw new Error('스탬프 적립에 실패했습니다.');
    },
  });
};

export default usePostEarnStamp;
