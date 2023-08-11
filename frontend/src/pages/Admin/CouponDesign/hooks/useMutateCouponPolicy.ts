import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { CouponSettingReq } from '../../../../types/api';
import { postCouponSetting } from '../../../../api/post';

export const useMutateCouponPolicy = () => {
  const navigate = useNavigate();
  // FIXME: 추후 카페 아이디 하드코딩된 값 제거
  const { mutate } = useMutation({
    mutationFn: (couponConfig: CouponSettingReq) => postCouponSetting(1, couponConfig),
    onSuccess: () => {
      navigate('/admin');
    },
  });

  return { mutate };
};
