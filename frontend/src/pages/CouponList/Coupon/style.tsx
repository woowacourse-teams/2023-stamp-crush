import { styled } from 'styled-components';

export const CouponWrapper = styled.img`
  width: 270px;
  height: 150px;
  position: absolute;
  top: 60%;
  bottom: 40%;
  transform: translate(50%, -50%);
  transition: transform 0.1s;
  object-fit: cover;
  box-shadow: 0px -10px 15px -2px rgba(0, 0, 0, 0.25);
`;
