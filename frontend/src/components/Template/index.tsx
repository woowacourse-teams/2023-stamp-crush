import { PropsWithChildren } from 'react';
import SideBar from '../SideBar';
import { BaseTemplate, Footer, PageContainer, SideBarWrapper } from './style';

import { AiOutlineMail } from 'react-icons/ai';

const Template = ({ children }: PropsWithChildren) => {
  return (
    <>
      <BaseTemplate>
        <SideBarWrapper>
          <SideBar />
        </SideBarWrapper>
        <PageContainer>{children}</PageContainer>
      </BaseTemplate>
      <Footer>
        <span>
          CONTACT <AiOutlineMail /> stampcrush@gmail.com
          <br />
        </span>
        <span>COPYRIGHT © 2023 STAMPCRUSH ALL RIGHTS RESERVED</span>
      </Footer>
    </>
  );
};

export default Template;
