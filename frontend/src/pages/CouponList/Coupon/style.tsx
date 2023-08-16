import { styled } from 'styled-components';

export const CouponWrapper = styled.button<{ $src: string }>`
  width: 315px;
  height: 175px;
  position: absolute;
  top: 60%;
  background-image: url(${({ $src }) => $src});
  background-size: 315px 175px;
  transform: translate(50%, -50%);
  transition: transform 0.1s;
  object-fit: cover;
  box-shadow: 0px -5px 10px -7px #aaa;
  z-index: 2;
  -webkit-tap-highlight-color: transparent;
`;

export const ImageForLoading = styled.img`
  display: none;
`;
