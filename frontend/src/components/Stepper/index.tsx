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
  const onIncreaseNumber = () => {
    if (value + step > maxValue) return;
    setValue(value + step);
  };

  const onDecreaseNumber = () => {
    if (value - step < minValue) return;
    setValue(value - step);
  };

  return (
    <StepperWrapper $height={height}>
      <LeftStepperButton $height={height} onClick={onIncreaseNumber}>
        +
      </LeftStepperButton>
      <BaseStepperInput $height={height} value={value} />
      <RightStepperButton $height={height} onClick={onDecreaseNumber}>
        -
      </RightStepperButton>
    </StepperWrapper>
  );
};

export default Stepper;
