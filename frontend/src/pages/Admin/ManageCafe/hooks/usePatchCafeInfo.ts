import { useMutation } from '@tanstack/react-query';
import { patchCafeInfo } from '../../../../api/patch';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../../constants';

const usePatchCafeInfo = () => {
  const navigate = useNavigate();
  return useMutation(patchCafeInfo, {
    onSuccess: () => {
      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      throw new Error('카페 정보 등록에 실패했습니다.');
    },
  });
};

export default usePatchCafeInfo;
