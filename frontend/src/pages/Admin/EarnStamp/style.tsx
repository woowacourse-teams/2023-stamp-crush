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

  gap: 10px;

  & > h1 {
    font-size: 20px;
    font-weight: 500;
  }

  & > span {
    font-size: 14px;
  }
`;

export const CouponSelectorContainer = styled.main`
  display: grid;
  grid-template-columns: repeat(2, 340px 150px);
  width: 700px;
  height: 450px;
  align-items: center;
  margin-top: 20px;

  color: #777;

  & > button {
    grid-column: 2/3;
    font-size: 18px;
  }
`;

export const StepperWrapper = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  width: 340px;
  height: 80px;
  gap: 10px;

  p {
    position: absolute;
    bottom: 0;
    font-size: 14px;
    color: dodgerblue;
    line-height: 20px;
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
