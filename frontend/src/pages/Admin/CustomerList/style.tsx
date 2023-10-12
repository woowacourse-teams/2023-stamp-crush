import { styled } from 'styled-components';

export const CustomerContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 40px;
  width: 100%;

  @media screen and (max-width: 450px) {
    margin-top: 10px;
    height: 95%;
  }
`;

export const CustomerBoxContainer = styled.div`
  display: flex;
  flex-direction: column;
  overflow: scroll;
  height: 540px;

  @media screen and (max-width: 450px) {
    height: 100%;
  }
`;

export const EmptyCustomers = styled.p`
  display: flex;
  flex-direction: column;
  margin: 180px 30px;
  line-height: 24px;
  text-align: center;
  color: gray;

  & > span {
    color: #222;
    font-size: 18px;
    font-weight: 900;
    margin-bottom: 10px;
  }
`;

export const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
  margin-top: 40px;
  padding: 0 20px;

  @media screen and (max-width: 450px) {
    gap: 10px;
    margin-top: 16px;
    padding: 0;
  }
`;

export const TabContainer = styled.div`
  display: flex;
  gap: 10px;

  @media screen and (max-width: 450px) {
    gap: 4px;
  }
`;

export const RegisterTypeTab = styled.button<{ $isSelected: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;
  border: ${({ $isSelected, theme }) => ($isSelected ? 'none' : `1px solid ${theme.colors.main}`)};
  border-radius: 5px;
  width: 60px;
  height: 30px;
  color: ${({ $isSelected }) => ($isSelected ? 'white' : '#222')};
  background: ${({ $isSelected, theme }) => ($isSelected ? theme.colors.main : 'transparent')};

  &:hover {
    opacity: 70%;
  }

  @media screen and (max-width: 450px) {
    width: 50px;
  }
`;
