import { styled } from 'styled-components';

interface StyledStepperProps {
  $height: number;
}

interface StepperButtonProps extends StyledStepperProps {
  $position: 'left' | 'right';
}

export const StepperWrapper = styled.div<StyledStepperProps>`
  display: flex;
  height: 50px;
`;

export const BaseStepperButton = styled.button<StepperButtonProps>`
  display: block;
  background-color: ${({ theme }) => theme.colors.main};
  border: 3px solid ${({ theme }) => theme.colors.main};
  font-size: 30px;
  font-weight: 600;
  color: ${({ theme }) => theme.colors.point};
  cursor: pointer;
  &:hover {
    opacity: 80%;
  }
  width: 45px;
  border-radius: ${({ $position }) => ($position === 'left' ? '10px 0 0 10px' : '0 10px 10px 0')};
`;

export const BaseStepperInput = styled.input<StyledStepperProps>`
  box-sizing: border-box;
  height: 50px;
  width: 50px;
  display: block;
  padding: 0;
  border: none;
  border-top: 3px solid ${({ theme }) => theme.colors.main};
  border-bottom: 3px solid ${({ theme }) => theme.colors.main};
  text-align: center;
  font-size: 20px;
  font-weight: 700;

  &:focus {
    outline: none;
  }
`;
