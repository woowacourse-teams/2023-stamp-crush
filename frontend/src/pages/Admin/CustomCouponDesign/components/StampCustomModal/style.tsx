import styled from 'styled-components';

export const BackCouponWrapper = styled.div`
  display: flex;
  width: 100%;
  height: 350px;

  align-items: center;
  justify-content: center;
  box-sizing: border-box;
`;

export const Stamp = styled.span<{
  $x: number;
  $y: number;
}>`
  display: flex;
  position: absolute;
  justify-content: center;
  align-items: center;
  left: ${(props) => props.$x}px;
  top: ${(props) => props.$y}px;
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
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
`;

export const StampImage = styled.img`
  object-fit: contain;
  width: 70px;
  height: 70px;
`;
