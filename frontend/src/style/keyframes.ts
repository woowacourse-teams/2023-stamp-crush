import { keyframes } from 'styled-components';

export const swap = keyframes`
  50% {
    transform: translateY(-200px) scale(0.55) rotate(-15deg);
    animation-timing-function: ease-in;
  }
  100% {
    transform: translateY(-45px) scale(0.85);
    z-index: -1;
  }
`;
