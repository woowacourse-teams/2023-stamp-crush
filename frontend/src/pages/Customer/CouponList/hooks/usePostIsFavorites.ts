import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postIsFavorites } from '../../../../api/post';
import { CouponRes } from '../../../../types/api/response';

const usePostIsFavorites = (closeModal: () => void, currentIndex: number) => {
  const queryClient = useQueryClient();

  return useMutation(postIsFavorites, {
    onSuccess: () => {
      closeModal();
    },
    onMutate: async () => {
      await queryClient.cancelQueries(['coupons']);
      queryClient.setQueryData<CouponRes>(['coupons'], (prev) => {
        if (!prev) return;
        prev.coupons[currentIndex].cafeInfo.isFavorites =
          !prev.coupons[currentIndex].cafeInfo.isFavorites;
        return undefined;
      });
    },
  });
};

export default usePostIsFavorites;
