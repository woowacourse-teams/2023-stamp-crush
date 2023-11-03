import styled from 'styled-components';
import { Z_INDEX } from '../../constants/magicNumber';

export const LoadingContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  background-color: transparent;
`;

export const CustomerLoadingContainer = styled(LoadingContainer)`
  position: absolute;
  width: 85%;
  height: 50vh;
  z-index: ${Z_INDEX.highest};
  background: transparent;
`;
