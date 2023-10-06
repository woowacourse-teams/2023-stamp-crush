import { useMutation } from '@tanstack/react-query';
import { postCustomerPhoneNumber } from '../../../../api/post';
import { removeHyphen } from '../../../../utils';

const usePostCustomerPhoneNumber = () => {
  return useMutation({
    mutationFn: (phoneNumber: string) =>
      postCustomerPhoneNumber({ body: { phoneNumber: removeHyphen(phoneNumber) } }),
  });
};

export default usePostCustomerPhoneNumber;
