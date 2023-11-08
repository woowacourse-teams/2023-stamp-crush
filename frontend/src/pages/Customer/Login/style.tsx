import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import { Z_INDEX } from '../../../constants/magicNumber';

export const BackgroundImage = styled.img`
  width: 100%;
  height: 100%;
  position: relative;
`;

export const Backdrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 120vh;
  z-index: ${Z_INDEX.above};
  background-color: rgba(0, 0, 0, 0.3);
`;

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: ${Z_INDEX.highest};

  width: 80%;
  background: #fcf8f2;
  padding: 40px 10px;
  border-radius: 10px;
  box-shadow: 0px -4px 8px 0 rgba(0, 0, 0, 0.1);
`;

export const ServiceIntro = styled.span`
  font-weight: 500;
  font-size: 22px;
`;

export const ServiceIntroSub = styled.span`
  margin-top: 16px;
  font-size: 16px;
  font-weight: 500;
  color: #674ea7;
`;

export const LogoImg = styled.img`
  height: 100px;
`;

export const LoginLink = styled.a`
  margin-top: 30px;
`;

export const KakaoLoginImg = styled.img`
  width: 215px;
  height: 50px;
`;

export const RedirectContainer = styled.div`
  margin-top: 18px;
  font-size: 14px;
`;

export const RedirectLink = styled(Link)`
  color: ${({ theme }) => theme.colors.gray400};
`;

export const Title = styled.h2`
  font-size: 36px;
  font-weight: 700;
`;

export const Template = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;

  max-width: 450px;
  margin: 0 auto;
  border-radius: 8px;

  @media screen and (min-width: 450px) {
    position: relative;

    margin: 0 auto;
  }
`;
