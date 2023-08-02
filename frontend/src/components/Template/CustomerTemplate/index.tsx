import { PropsWithChildren } from 'react';
import { BaseCustomerTemplate, ContentContainer } from './style';

const CustomerTemplate = ({ children }: PropsWithChildren) => {
  return (
    <BaseCustomerTemplate>
      <ContentContainer>{children}</ContentContainer>
    </BaseCustomerTemplate>
  );
};

export default CustomerTemplate;
