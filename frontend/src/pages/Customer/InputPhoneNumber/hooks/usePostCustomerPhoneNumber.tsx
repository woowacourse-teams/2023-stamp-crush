import { useMutation } from '@tanstack/react-query';
import { postCustomerPhoneNumber } from '../../../../api/post';

const usePostCustomerPhoneNumber = () => {
  return useMutation({
    mutationFn: (phoneNumber: string) => postCustomerPhoneNumber({ body: { phoneNumber } }),
  });
};

export default usePostCustomerPhoneNumber;
