import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { postCouponSetting } from '../../../../api/post';
import { ROUTER_PATH } from '../../../../constants';

export const useMutateCouponPolicy = () => {
  const navigate = useNavigate();
  // FIXME: 추후 카페 아이디 하드코딩된 값 제거

  const { mutate } = useMutation({
    mutationFn: postCouponSetting,
    onSuccess: () => {
      navigate(ROUTER_PATH.customerList);
    },
  });

  return { mutate };
};
