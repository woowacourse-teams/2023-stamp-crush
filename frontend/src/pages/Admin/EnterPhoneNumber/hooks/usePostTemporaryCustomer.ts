import { useMutation } from '@tanstack/react-query';
import { postTemporaryCustomer } from '../../../../api/post';

const usePostTemporaryCustomer = () =>
  useMutation({
    mutationFn: postTemporaryCustomer,
    onError: () => {
      throw new Error('[ERROR] 임시 가입 고객 생성에 실패하였습니다.');
    },
  });

export default usePostTemporaryCustomer;
