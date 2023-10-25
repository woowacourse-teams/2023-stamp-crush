import React from 'react';
import { Container, ToggleSwitch } from './style';

interface ToggleButtonProps {
  isOn: boolean;
  toggle: () => void;
}

const ToggleButton = ({ isOn, toggle }: ToggleButtonProps) => {
  return (
    <Container $isOn={isOn} onClick={toggle}>
      <ToggleSwitch $isOn={isOn} />
    </Container>
  );
};

export default ToggleButton;
