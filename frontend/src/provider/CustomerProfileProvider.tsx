import { PropsWithChildren, createContext, useEffect } from 'react';
import { useCustomerProfile } from '../hooks/useCustomerProfile';
import { CustomerProfileRes } from '../types/api/response';
import { useLocation, useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../constants/routerPath';

const CustomerProfileContext = createContext<CustomerProfileRes | undefined>(undefined);

const CustomerProfileProvider = ({ children }: PropsWithChildren) => {
  const navigate = useNavigate();
  const location = useLocation();
  const { customerProfile, status } = useCustomerProfile();

  useEffect(() => {
    if (
      status === 'success' &&
      !customerProfile.profile.phoneNumber &&
      location.pathname !== ROUTER_PATH.inputPhoneNumber
    ) {
      alert('전화번호 등록 후 사용해주세요.');
      navigate(ROUTER_PATH.inputPhoneNumber);
    }
  }, [customerProfile]);

  return (
    <CustomerProfileContext.Provider value={customerProfile}>
      {children}
    </CustomerProfileContext.Provider>
  );
};

export default CustomerProfileProvider;
