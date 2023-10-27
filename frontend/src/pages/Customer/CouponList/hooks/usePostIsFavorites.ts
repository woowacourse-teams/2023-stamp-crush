import { useMutation } from '@tanstack/react-query';
import { postIsFavorites } from '../../../../api/post';

const usePostIsFavorites = (closeModal: () => void) => {
  return useMutation(postIsFavorites, {
    onSuccess: () => {
      closeModal();
    },
  });
};

export default usePostIsFavorites;
