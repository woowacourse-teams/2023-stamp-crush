import styled from 'styled-components';

export const EarnStampContainer = styled.main`
  display: flex;
  flex-direction: column;

  width: fit-content;
  margin-top: 48px;

  & > button {
    margin-left: auto;
  }
`;

export const CouponStepperWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const StepperGuide = styled.p`
  font-size: 20px;
  font-weight: 600;
  line-height: 25px;
`;
