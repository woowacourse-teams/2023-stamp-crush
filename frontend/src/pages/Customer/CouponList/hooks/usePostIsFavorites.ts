import { QueryClient, useMutation } from '@tanstack/react-query';
import { postIsFavorites } from '../../../../api/post';
import { CouponRes } from '../../../../types/api/response';

const usePostIsFavorites = (
  closeModal: () => void,
  queryClient: QueryClient,
  currentIndex: number,
) => {
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
