import { keyframes } from 'styled-components';

export const swap = keyframes`
  50% {
    transform: translateY(-250px) scale(0.55) rotate(-15deg);
    animation-timing-function: ease-in;
  }
  100% {
    transform: translateY(-45px) scale(0.85);
    z-index: -1;
  }
`;

export const detail = keyframes`
  0% {
    transform: translateY(15px) scale(1.05);
    display: flex;
  }
  
  100%{
    transform: translateY(-219%) scale(0.86);
  }
`;

export const popup = keyframes`
  0% {
    transform: translateY(100%);
  }

  100% {
    transform: translateY(0);
  }
`;

export const popup = keyframes`
  0% {
    transform: translateY(100%);
  }

  100% {
    transform: translateY(0);
  }
`;
