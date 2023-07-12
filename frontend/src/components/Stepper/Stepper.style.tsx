import { styled } from 'styled-components';

interface StyledStepperProps {
  $height: number;
}
export const StepperWrapper = styled.div<StyledStepperProps>`
  display: flex;
  height: ${({ $height }) => `${$height}px`};
`;

export const BaseStepperButton = styled.button<StyledStepperProps>`
  display: block;
  background-color: ${({ theme }) => theme.colors.white};
  border: 1px solid ${({ theme }) => theme.colors.black};
  cursor: pointer;
  &:hover {
    background-color: ${({ theme }) => theme.colors.gray100};
  }
`;

export const LeftStepperButton = styled(BaseStepperButton)`
  border-radius: 3px 0 0 3px;
  width: ${({ $height }) => `${$height}px`};
`;

export const RightStepperButton = styled(BaseStepperButton)`
  border-radius: 0 3px 3px 0;
  width: ${({ $height }) => `${$height}px`};
`;

export const BaseStepperInput = styled.input<StyledStepperProps>`
  box-sizing: border-box;
  height: ${({ $height }) => `${$height}px`};
  width: ${({ $height }) => `${$height * 2}px`};
  display: block;
  padding: 0;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.colors.black};
  border-bottom: 1px solid ${({ theme }) => theme.colors.black};
  text-align: center;
`;
