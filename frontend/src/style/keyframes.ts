import { keyframes } from 'styled-components';
import { Z_INDEX } from '../constants/magicNumber';

export const swap = keyframes`
  50% {
    transform: translateY(-250px) scale(0.55) rotate(-15deg);
    animation-timing-function: ease-in;
  }
  100% {
    transform: translateY(-45px) scale(0.85);
    z-index: ${Z_INDEX.below};
  }
`;

export const popup = keyframes`
  0% {
    transform: translateY(100%);
    opacity: 10%;
  }
  100% {
    transform: translateY(0);
    opacity: 100%;
  }
`;

export const skeletonLoading = keyframes`
  0% {
    background-position: -200% 0;
  }
  100% {
    background-position: 200% 0;
  }
`;
