import { Link } from 'react-router-dom';
import { styled } from 'styled-components';

export const Container = styled.div`
  font-family: sans-serif;
  display: flex;
  width: 100vw;
  height: 100vh;
`;

export const LogoImg = styled.img`
  width: 200px;
`;

export const LoginLink = styled.a``;

export const KakaoLoginImg = styled.img`
  width: 220px;
`;

export const LoginContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 37%;
`;

export const BackgroundImg = styled.img`
  height: 100vh;
  width: 63%;
  object-fit: cover;
`;

export const Title = styled.h1`
  font-size: 32px;
  font-weight: 500;
  margin-top: 40px;
`;

export const SubTitle = styled.h2`
  margin-top: 6px;
  font-size: 34px;
  font-weight: 300;
`;

export const Text = styled.span`
  margin: 80px 0 30px 0;
  font-size: 18px;
`;

export const RedirectContainer = styled.div`
  margin-top: 24px;
  font-size: 14px;
`;

export const RedirectLink = styled(Link)`
  color: ${({ theme }) => theme.colors.gray400};
`;

export const CheckItemContainer = styled.div`
  display: flex;
  height: 20px;
  align-items: center;
  font-size: 16px;
  text-align: center;
  color: #2a1d1f;
  font-weight: 300;
`;

export const CheckIcon = styled.img`
  width: 18px;
  height: 18px;
  margin-right: 14px;
`;

export const CheckList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 90px;
`;
