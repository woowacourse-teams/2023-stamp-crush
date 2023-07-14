import { PropsWithChildren } from 'react';
import { BaseTemplate } from './Template.style';
import PageSideBar from '../SideBar/PageSideBar';

const Template = ({ children }: PropsWithChildren) => {
  return (
    <BaseTemplate>
      <PageSideBar />
      {children}
    </BaseTemplate>
  );
};

export default Template;
