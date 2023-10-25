import { styled, keyframes, css } from 'styled-components';

const fold = keyframes`
from  { 
    margin-top: 16px;
    margin-bottom: 32px;
  }
  to {
    margin: 0px; 
  }
`;

const bounce = keyframes`
  from  { margin: 0px; }
  to {
    margin-top: 16px;
    margin-bottom: 32px;
  }
`;

export const CouponWrapper = styled.button<{ $src: string; $isOn: boolean; $index: number }>`
  width: 315px;
  min-height: 175px;
  background-image: url(${({ $src }) => $src});
  background-size: cover;
  background-position: center;
  box-shadow: 0px 0px 10px 1px #aaa;
  z-index: 2;
  -webkit-tap-highlight-color: transparent;
  z-index: ${({ $index }) => $index};
  ${({ $isOn, $index }) =>
    $isOn &&
    css`
      position: absolute;
      animation: ${fold} 0.5s ease-in;
      transform: translateY(${$index * 3}rem);
      transition: animation 0.5s ease-in;
    `}
  ${({ $isOn, $index }) =>
    !$isOn &&
    css`
      animation: ${bounce} 0.5s ease ${$index * 0.05}s forwards;
      transition: animation 0.5s ease-in;
    `}
`;

export const ImageForLoading = styled.img`
  display: none;
`;
