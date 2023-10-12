import { styled } from 'styled-components';

export const BaseTemplate = styled.main`
  display: flex;
  margin: 0 auto;
  width: 100vw;
  height: 100vh;
  background: ${({ theme }) =>
    `linear-gradient(to bottom, ${theme.colors.main} 60%, ${theme.colors.point} 100%)`};

  @media screen and (max-width: 450px) {
    flex-direction: column;
    align-items: center;
  }
`;

export const SideBarWrapper = styled.section`
  background: white;

  @media screen and (max-width: 450px) {
    display: none;
  }
`;

export const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 0 40px;
  margin: 20px 20px 20px 0;
  background: white;
  border-radius: 20px;
  box-shadow: 7px 5px 5px 3px rgba(0, 0, 0, 0.25);
  overflow: scroll;

  @media screen and (max-width: 450px) {
    width: 90vw;
    height: 100%;
    margin: 0 16px;
    padding: 16px;
  }
`;

export const Footer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  width: 100vw;
  height: 150px;
  padding-top: 40px;
  background: ${({ theme }) => theme.colors.point};

  & > span {
    display: flex;
    align-items: center;
    gap: 5px;
    font-size: 14px;
    color: #eee;
  }
`;

export const LogoWrapper = styled.div`
  display: none;

  @media screen and (max-width: 450px) {
    display: flex;
    align-items: center;
    width: 90vw;
    padding: 10px 0;
  }
`;
