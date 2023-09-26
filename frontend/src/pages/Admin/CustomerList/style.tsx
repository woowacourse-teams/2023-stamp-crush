import { styled } from 'styled-components';

export const CustomerContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 40px;
  width: 100%;
`;

export const CustomerBoxContainer = styled.div`
  display: flex;
  flex-direction: column;
  overflow: scroll;
  height: 540px;
`;

export const EmptyCustomers = styled.p`
  display: flex;
  margin: 80px 10px;
`;

export const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
  margin-top: 40px;
  padding: 0 20px;
`;

export const TabContainer = styled.div`
  display: flex;
  gap: 10px;
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
`;
