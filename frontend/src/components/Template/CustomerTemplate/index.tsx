import { PropsWithChildren } from 'react';
import { BaseCustomerTemplate, ContentContainer } from './style';
import BottomTabBar from './BottomTabBar';

const CustomerTemplate = ({ children }: PropsWithChildren) => {
  return (
    <BaseCustomerTemplate>
      <ContentContainer>
        {children}
        <BottomTabBar />
      </ContentContainer>
    </BaseCustomerTemplate>
  );
};

export default CustomerTemplate;
