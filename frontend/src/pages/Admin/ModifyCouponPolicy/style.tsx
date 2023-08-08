import { styled } from 'styled-components';

export const ButtonContainer = styled.div<{ $step: number }>`
  display: flex;
  justify-content: ${({ $step }) => ($step === 1 ? 'flex-end' : 'space-between')};
`;

export const StepContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 40%;
  height: 60%;
`;

export const StepWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;
