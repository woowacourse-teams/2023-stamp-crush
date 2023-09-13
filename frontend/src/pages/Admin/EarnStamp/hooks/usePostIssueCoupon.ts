import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postIssueCoupon } from '../../../../api/post';
import { IssueCouponRes } from '../../../../types/api/response';
import { CustomerPhoneNumber } from '../../../../types/domain/customer';

const usePostIssueCoupon = (cafeId: number, customer: CustomerPhoneNumber) => {
  const queryClient = useQueryClient();

  return useMutation<IssueCouponRes>({
    mutationFn: async () => {
      if (!customer) throw new Error('고객 정보를 불러오지 못했습니다.');
      return await postIssueCoupon({
        params: { customerId: customer.id },
        body: {
          cafeId,
        },
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['coupon'] });
    },
    onError: () => {
      throw new Error('스탬프 적립에 실패했습니다.');
    },
  });
};

export default usePostIssueCoupon;
