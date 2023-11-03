import { Dispatch, KeyboardEvent, SetStateAction } from 'react';
import { BaseStepperButton, BaseStepperInput, StepperWrapper } from './style';

interface StepperProps {
  value: number;
  minValue?: number;
  maxValue?: number;
  step?: number;
  height?: number;
  onSubmit: () => void;
  setValue: Dispatch<SetStateAction<number>>;
}

const Stepper = ({
  value,
  minValue = 1,
  maxValue = 10,
  step = 1,
  height = 42,
  onSubmit,
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

  const pressDownKey = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'ArrowDown') {
      decreaseNumber();
    }
    if (e.key === 'ArrowUp') {
      increaseNumber();
    }
    if (e.key === 'Enter') {
      onSubmit();
    }
  };

  return (
    <StepperWrapper $height={height}>
      <BaseStepperButton $position="left" $height={height} onClick={decreaseNumber}>
        -
      </BaseStepperButton>
      <BaseStepperInput autoFocus $height={height} value={value} onKeyDown={pressDownKey} />
      <BaseStepperButton $position="right" $height={height} onClick={increaseNumber}>
        +
      </BaseStepperButton>
    </StepperWrapper>
  );
};

export default Stepper;
