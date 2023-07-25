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
  background-color: transparent;
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
  transform: translate(-50%, -50%);
`;

export const StampBadge = styled.span`
  display: flex;
  position: absolute;
  top: -10px;
  right: -10px;
  justify-content: center;
  width: 20px;
  height: 20px;
  background-color: yellow;
  border: 1px solid black;
  border-radius: 50%;
  line-height: 20px;
`;

export const BackImage = styled.img`
  width: 540px;
  height: 300px;
  z-index: -1;
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
`;
