import styled from 'styled-components';

type StampProps = {
  X: number;
  Y: number;
};

export const BackCouponWrapper = styled.div`
  width: 540px;
  height: 300px;
  border: 1px solid black;
  box-sizing: border-box;
`;

export const Stamp = styled.span<StampProps>`
  display: flex;
  position: absolute;
  justify-content: center;
  align-items: center;
  left: ${(props) => props.X}px;
  top: ${(props) => props.Y}px;
  width: 50px;
  height: 50px;
`;

export const StampBadge = styled.span`
  display: flex;
  position: absolute;
  top: 0;
  right: 0;
  justify-content: center;
  width: 20px;
  height: 20px;
  background-color: yellow;
  border: 1px solid black;
  border-radius: 50%;
  line-height: 20px;
`;
