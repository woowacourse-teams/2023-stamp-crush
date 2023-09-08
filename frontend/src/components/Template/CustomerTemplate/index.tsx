import { PropsWithChildren } from 'react';
import { BaseCustomerTemplate, ContentContainer } from './style';
import BottomTabBar from './BottomTabBar';
import { useLocation } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';

const CustomerTemplate = ({ children }: PropsWithChildren) => {
  const location = useLocation();

  return (
    <BaseCustomerTemplate $isHome={location.pathname === ROUTER_PATH.couponList}>
      <ContentContainer>
        {children}
        <BottomTabBar />
      </ContentContainer>
    </BaseCustomerTemplate>
  );
};

export default CustomerTemplate;
