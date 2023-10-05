import { styled } from 'styled-components';

export const CreatedTypePageContainer = styled.div`
  display: flex;
  gap: 40px;
`;

export const ButtonContainer = styled.div<{ $step: number }>`
  display: flex;
  justify-content: ${({ $step }) => ($step === 1 ? 'flex-end' : 'space-between')};
`;

export const StepContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 60%;
  max-width: 500px;
  height: 550px;
`;

export const StepWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;
