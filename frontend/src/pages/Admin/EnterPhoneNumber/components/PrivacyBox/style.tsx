import styled from 'styled-components';

export const Container = styled.section`
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

  @media screen and (max-width: 768px) {
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
