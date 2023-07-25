import React from 'react';
import { BaseStepperButton, BaseStepperInput, StepperWrapper } from './style';

interface StepperProps {
  minValue?: number;
  maxValue?: number;
  step?: number;
  value: number;
  setValue: React.Dispatch<React.SetStateAction<number>>;
  height?: number;
}

const Stepper = ({
  minValue = 0,
  maxValue = 10,
  step = 1,
  value,
  setValue,
  height = 42,
}: StepperProps) => {
  const increaseNumber = () => {
    if (value + step > maxValue) return;
    setValue(value + step);
  };

  const decreaseNumber = () => {
    if (value - step < minValue) return;
    setValue(value - step);
  };

  return (
    <StepperWrapper $height={height}>
      <BaseStepperButton $position="left" $height={height} onClick={increaseNumber}>
        +
      </BaseStepperButton>
      <BaseStepperInput $height={height} value={value} />
      <BaseStepperButton $position="right" $height={height} onClick={decreaseNumber}>
        -
      </BaseStepperButton>
    </StepperWrapper>
  );
};

export default Stepper;
