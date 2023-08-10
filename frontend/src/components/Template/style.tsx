import { styled } from 'styled-components';

export const BaseTemplate = styled.main`
  display: flex;
  flex-direction: row;
  margin: 0 auto;
  width: 100vw;
  height: 100vh;
  background: ${({ theme }) => theme.colors.main};
`;

export const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 0 40px;
  margin: 20px 20px 20px 0;
  background: white;
  border-radius: 20px;
  box-shadow: 7px 5px 5px 3px rgba(0, 0, 0, 0.25);
`;
