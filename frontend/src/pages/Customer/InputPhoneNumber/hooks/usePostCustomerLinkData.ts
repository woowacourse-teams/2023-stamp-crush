import { useMutation } from '@tanstack/react-query';
import { postCustomerLinkData } from '../../../../api/post';

const usePostCustomerLinkData = () => {
  return useMutation({
    mutationFn: (customerId: number) => postCustomerLinkData({ body: { id: customerId } }),
  });
};

export default usePostCustomerLinkData;
