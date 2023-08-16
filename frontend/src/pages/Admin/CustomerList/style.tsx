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
  align-self: flex-end;
  align-items: center;
  gap: 20px;
  margin-top: 30px;
  margin-right: 30px;
`;
