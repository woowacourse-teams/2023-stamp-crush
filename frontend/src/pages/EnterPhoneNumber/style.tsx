import { styled } from 'styled-components';

export const Title = styled.header`
  width: 100vw;
  height: 110px;
  border-bottom: 3px solid ${({ theme }) => theme.colors.gray400};
  padding: auto 0;
  line-height: 110px;

  text-align: center;
  font-size: 30px;
  font-weight: 600;
`;

export const Container = styled.div`
  display: flex;
  height: 100%;
`;

export const PrivacyBox = styled.section`
  padding: 50px 10%;
  width: 53%;

  & > p {
    font-size: 16px;
    line-height: 24px;
  }

  & > h1 {
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 15px;
  }
`;
