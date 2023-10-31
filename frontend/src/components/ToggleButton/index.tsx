import React from 'react';
import { Container, ToggleSwitch } from './style';

interface ToggleButtonProps {
  isOn: boolean;
  disabled?: boolean;
  toggle: () => void;
}

const ToggleButton = ({ isOn, toggle, disabled = false }: ToggleButtonProps) => {
  const clickToggleButton = () => {
    if (disabled) return;

    toggle();
  };

  return (
    <Container $isOn={isOn} onClick={clickToggleButton}>
      <ToggleSwitch $isOn={isOn} />
    </Container>
  );
};

export default ToggleButton;
