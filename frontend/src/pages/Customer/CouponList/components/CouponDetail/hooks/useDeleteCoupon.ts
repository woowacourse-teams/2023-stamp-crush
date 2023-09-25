import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteCoupon } from '../../../../../../api/delete';

const useDeleteCoupon = (id: number, closeDetail: () => void, closeModal: () => void) => {
  const queryClient = useQueryClient();

  return useMutation(() => deleteCoupon(id), {
    onSuccess: () => {
      closeDetail();
      queryClient.invalidateQueries(['coupons']);

      closeModal();
    },
  });
};

export default useDeleteCoupon;
