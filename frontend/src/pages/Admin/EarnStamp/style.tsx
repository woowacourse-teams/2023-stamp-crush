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

export const CouponIndicatorWrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 480px;
  gap: 10px;

  & > h1 {
    font-size: 20px;
    font-weight: 500;
  }

  & > span {
    font-size: 14px;
  }
`;

export const EarnStampContainer = styled.main`
  display: grid;
  grid-template-columns: repeat(2, 340px 150px);
  width: 700px;
  height: 450px;
  align-items: center;
  margin-top: 20px;

  color: #777;
`;

export const StepperWrapper = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 340px;
  height: 250px;
  gap: 250px;

  p {
    position: absolute;
    bottom: 220px;
    font-size: 14px;
    color: dodgerblue;
    line-height: 20px;
  }

  & > :last-child {
    width: 140px;
    font-size: 18px;
  }
`;

export const TextWrapper = styled.div`
  display: flex;
  width: 300px;
  align-items: center;
  gap: 8px;

  & :nth-child(n) {
    font-size: 18px;
  }

  & :nth-child(2) {
    width: 100px;
    color: dodgerblue;
    font-weight: bold;
  }
`;
