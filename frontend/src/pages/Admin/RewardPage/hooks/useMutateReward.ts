import { useMutation } from '@tanstack/react-query';
import {
  CustomerIdParams,
  MutateReq,
  RewardIdParams,
  RewardReqBody,
} from '../../../../types/api/request';
import { patchReward } from '../../../../api/patch';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../../constants/routerPath';

const useMutateReward = () => {
  const navigate = useNavigate();
  return useMutation({
    mutationFn: (request: MutateReq<RewardReqBody, RewardIdParams & CustomerIdParams>) => {
      return patchReward(request);
    },
    onSuccess() {
      navigate(ROUTER_PATH.customerList);
    },
    onError() {
      alert('에러가 발생했습니다. 네트워크 상태를 확인해주세요.');
    },
  });
};

export default useMutateReward;
