import { useMutation } from '@tanstack/react-query';
import { postCustomerPhoneNumber } from '../../../../api/post';
import { removeHyphen } from '../../../../utils';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../../constants';

const usePostCustomerPhoneNumber = () => {
  const navigate = useNavigate();
  return useMutation({
    mutationFn: (phoneNumber: string) =>
      postCustomerPhoneNumber({ body: { phoneNumber: removeHyphen(phoneNumber) } }),
    onSuccess: () => {
      navigate(ROUTER_PATH.greeting);
    },
  });
};

export default usePostCustomerPhoneNumber;
