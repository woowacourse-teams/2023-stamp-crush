import styled from 'styled-components';

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

export const CouponSelectorWrapper = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 0;
  right: -350px;

  & > h1 {
    font-size: 20px;
    font-weight: 500;
  }

  & > span {
    font-size: 14px;
  }
`;

export const CouponSelectorContainer = styled.main`
  display: flex;
  flex-direction: column;
  width: 400px;
  margin-top: 40px;
  height: 400px;
  position: relative;

  & > button {
    position: absolute;
    bottom: 0;
    right: 0;
  }
`;
