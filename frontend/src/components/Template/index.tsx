import { PropsWithChildren } from 'react';
import SideBar from '../SideBar';
import { BaseTemplate, Footer, LogoWrapper, PageContainer, SideBarWrapper } from './style';
import { AiOutlineMail } from '@react-icons/all-files/ai/AiOutlineMail';
import BottomTabBar from './CustomerTemplate/BottomTabBar';
import { StampcrushWhiteLogo } from '../../assets';

const Template = ({ children }: PropsWithChildren) => {
  return (
    <>
      <BaseTemplate>
        <LogoWrapper>
          <StampcrushWhiteLogo width={130} />
        </LogoWrapper>
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
      <BottomTabBar userType="admin" />
    </>
  );
};

export default Template;
