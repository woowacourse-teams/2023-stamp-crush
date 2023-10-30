import { styled, css } from 'styled-components';

export const CouponWrapper = styled.button<{ $src: string; $isOn: boolean; $index: number }>`
  width: 315px;
  min-height: 175px;

  background-image: url(${({ $src }) => $src});
  background-size: cover;
  background-position: center;

  box-shadow: 0px 0px 10px 1px rgba(0, 0, 0, 0.2);
  border-radius: 0 0 10px 10px;
  -webkit-tap-highlight-color: transparent;
  z-index: ${({ $index }) => $index};
  transition: transform 0.4s ease-in-out;
  position: relative;

  ${({ $isOn, $index }) =>
    $isOn
      ? css`
          transform: translateY(-${$index * 6}rem);
          transform: translateZ(${$index});
        `
      : css`
          transform: translateY(${$index * 2}rem);
        `}
`;

export const ImageForLoading = styled.img`
  display: none;
`;

export const StarIconWrapper = styled.button`
  position: absolute;
  top: 8px;
  right: 8px;
  background: transparent;
  border: none;

  &:active {
    opacity: 60%;
    transform: scale(1.05);
  }
`;

export const ProgressBarWrapper = styled.div`
  width: 315px;
  position: absolute;
  top: -10px;
  right: 0;
  left: 0;
  border-radius: 10px 10px 0 0;
  background: pink;
`;
