import { styled, css } from 'styled-components';

export const CouponWrapper = styled.button<{ $src: string; $isOn: boolean; $index: number }>`
  width: 315px;
  min-height: 175px;

  background-image: url(${({ $src }) => $src});
  background-size: cover;
  background-position: center;

  box-shadow: 0px 0px 10px 1px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
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
          transform: translateY(${$index * 1}rem);
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
