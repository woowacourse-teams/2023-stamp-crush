import styled from 'styled-components';

export const LoadingContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
`;

export const CustomerLoadingContainer = styled(LoadingContainer)`
  position: absolute;

  z-index: 999;
  background: transparent;
`;
