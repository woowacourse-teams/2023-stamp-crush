import styled, { keyframes } from 'styled-components';

const loadingAnimation = keyframes`
    0% {
        transform: translateY(-120%) translateX(40%) rotateZ(90deg);
    }
    75%{
        
        transform: translateY(10%);
    }
    100%{
        transform: translateY(0);
    }
`;

export const LoadingContainer = styled.div`
  position: absolute;

  top: calc(50vh - 20px);
  left: calc(50% - 20px);
  background-color: transparent;
  animation: ${loadingAnimation} 0.5s infinite;
`;
