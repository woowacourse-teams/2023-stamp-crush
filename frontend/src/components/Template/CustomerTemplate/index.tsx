import { PropsWithChildren } from 'react';
import { BaseCustomerTemplate, ContentContainer } from './style';
import BottomTabBar from './BottomTabBar';
import { useLocation } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { RouterPath } from '../../../types/utils';

const CustomerTemplate = ({ children }: PropsWithChildren) => {
  const location = useLocation();

  const isTabBarVisible = [
    ROUTER_PATH.couponList,
    ROUTER_PATH.rewardList,
    ROUTER_PATH.myPage,
  ].includes(location.pathname as RouterPath);

  return (
    <BaseCustomerTemplate $isHome={location.pathname === ROUTER_PATH.couponList}>
      <ContentContainer>
        {children}
        {isTabBarVisible && <BottomTabBar />}
      </ContentContainer>
    </BaseCustomerTemplate>
  );
};

export default CustomerTemplate;
