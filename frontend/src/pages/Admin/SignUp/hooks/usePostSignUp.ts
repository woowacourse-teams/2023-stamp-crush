import { useMutation } from '@tanstack/react-query';
import { postAdminSignUp } from '../../../../api/post';

const usePostSignUp = () => {
  return useMutation(postAdminSignUp, {
    onSuccess: () => {
      alert('스탬프크러쉬 서비스에 오신 것을 환영합니다!');
    },
  });
};

export default usePostSignUp;
