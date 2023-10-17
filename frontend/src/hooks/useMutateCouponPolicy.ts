import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { postCouponSetting } from '../api/post';
import ROUTER_PATH from '../constants/routerPath';

export const useMutateCouponPolicy = () => {
  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: postCouponSetting,
    onSuccess: () => {
      navigate(ROUTER_PATH.customerList);
    },
  });

  return { mutate };
};
