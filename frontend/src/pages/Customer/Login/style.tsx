import { Link } from 'react-router-dom';
import { styled } from 'styled-components';

export const Container = styled.div`
  font-family: sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;

  & > img {
    width: 200px;
  }
`;

export const ServiceIntro = styled.span`
  margin-top: 42px;
  font-weight: 400;
  font-size: 20px;
`;

export const ServiceIntroSub = styled.span`
  margin-top: 8px;
  font-weight: 300;
  font-size: 16px;
`;

export const LogoImg = styled.img`
  height: 100px;
`;

export const LoginLink = styled.a`
  margin-top: 42px;
`;

export const KakaoLoginImg = styled.img`
  width: 240px;
  height: 50px;
`;

export const RedirectContainer = styled.div`
  margin-top: 18px;
  font-size: 12px;
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
