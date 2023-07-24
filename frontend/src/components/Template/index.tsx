import { PropsWithChildren } from 'react';
import { BaseTemplate, PageContainer } from './style';
import PageSideBar from '../SideBar/PageSideBar';

const Template = ({ children }: PropsWithChildren) => {
  return (
    <BaseTemplate>
      <PageSideBar />
      <PageContainer>{children}</PageContainer>
    </BaseTemplate>
  );
};

export default Template;
