import { Dispatch, SetStateAction } from 'react';
import { BaseStepperButton, BaseStepperInput, StepperWrapper } from './style';

interface StepperProps {
  value: number;
  minValue?: number;
  maxValue?: number;
  step?: number;
  height?: number;
  setValue: Dispatch<SetStateAction<number>>;
}

const Stepper = ({
  value,
  minValue = 1,
  maxValue = 10,
  step = 1,
  height = 42,
  setValue,
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
      <BaseStepperButton $position="left" $height={height} onClick={decreaseNumber}>
        -
      </BaseStepperButton>
      <BaseStepperInput $height={height} value={value} />
      <BaseStepperButton $position="right" $height={height} onClick={increaseNumber}>
        +
      </BaseStepperButton>
    </StepperWrapper>
  );
};

export default Stepper;
