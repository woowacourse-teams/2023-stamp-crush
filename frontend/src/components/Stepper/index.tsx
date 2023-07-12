import React from 'react';
import {
  BaseStepperInput,
  LeftStepperButton,
  RightStepperButton,
  StepperWrapper,
} from './Stepper.style';

interface StepperProps {
  minValue: number;
  maxValue: number;
  step: number;
  value: number;
  setValue: React.Dispatch<React.SetStateAction<number>>;
  height: number;
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
      <LeftStepperButton $height={height} onClick={increaseNumber}>
        +
      </LeftStepperButton>
      <BaseStepperInput $height={height} value={value} />
      <RightStepperButton $height={height} onClick={decreaseNumber}>
        -
      </RightStepperButton>
    </StepperWrapper>
  );
};

export default Stepper;
