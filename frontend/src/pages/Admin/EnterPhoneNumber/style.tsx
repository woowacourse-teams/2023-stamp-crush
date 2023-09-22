import { styled } from 'styled-components';

export const Title = styled.header`
  position: relative;
  width: 100vw;
  height: 110px;
  border-bottom: 3px solid ${({ theme }) => theme.colors.gray400};
  padding: auto 0;
  line-height: 110px;

  text-align: center;
  font-size: 30px;
  font-weight: 600;
`;

export const IconWrapper = styled.div`
  display: flex;
  align-items: center;
  position: absolute;
  left: 30px;
  top: 0;
  bottom: 0;
`;

export const Container = styled.div`
  display: flex;
  height: 100%;
  @media screen and (max-width: 700px) {
    justify-content: center;
  }
`;

export const PrivacyBox = styled.section`
  padding: 50px 5%;
  width: 53%;

  & > p {
    font-size: 16px;
    line-height: 24px;
    margin-bottom: 20px;
  }

  & > h1 {
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 30px;
  }

  @media screen and (max-width: 700px) {
    display: none;
  }
`;

export const RowContainer = styled.div`
  display: grid;
  grid-template-columns: 2.8fr 1.2fr 2fr;
  grid-template-rows: 1;
  border: 1px solid #888;
  width: 75%;

  &:last-child {
    border-top: none;
  }
`;

export const TableTitleItem = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  background: ${({ theme }) => theme.colors.gray200};
  font-size: 12px;
  width: 100%;
  height: 30px;
`;

export const TableItem = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  color: black;
  width: 100%;
  padding: 10px 10px;
  font-size: 12px;
  line-height: 24px;
`;

export const PageContainer = styled.div`
  width: 100vw;
  height: 87vh;
`;
