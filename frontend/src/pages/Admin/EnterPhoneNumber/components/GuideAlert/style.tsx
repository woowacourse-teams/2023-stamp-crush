import styled from 'styled-components';
import { Z_INDEX } from '../../../../../constants/magicNumber';

export const BackDrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: ${Z_INDEX.highest};
  background-color: rgba(0, 0, 0, 0.3);
`;
