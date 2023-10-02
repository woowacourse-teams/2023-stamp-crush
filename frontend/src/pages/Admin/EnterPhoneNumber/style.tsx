import { styled } from 'styled-components';

export const Title = styled.header`
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  width: 100%;
  height: 80px;
  border-bottom: 3px solid ${({ theme }) => theme.colors.gray400};
  padding: auto 0;
  line-height: 110px;
  font-size: 26px;
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
  height: auto;

  @media screen and (max-width: 700px) {
    justify-content: center;
  }
`;

export const PrivacyBox = styled.section`
  padding: 50px 5%;

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
  grid-template-columns: 2.8fr 1.2fr 2fr 2fr;
  grid-template-rows: 1;
  border: 1px solid #888;
  width: 100%;

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
  text-align: center;

  color: black;
  width: 100%;
  padding: 10px 10px;
  font-size: 12px;
  line-height: 24px;
`;
