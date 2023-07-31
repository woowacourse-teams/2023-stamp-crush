import { styled } from 'styled-components';

export const CouponWrapper = styled.img`
  width: 315px;
  height: 175px;
  position: absolute;
  top: 60%;
  transform: translate(50%, -50%);
  transition: transform 0.1s;
  object-fit: cover;
  box-shadow: 0px -2px 15px -2px #888;
  -webkit-tap-highlight-color: transparent;
`;
