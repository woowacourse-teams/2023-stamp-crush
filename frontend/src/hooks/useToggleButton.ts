import { useState } from 'react';

const useToggleButton = () => {
  const [isOn, setIsOn] = useState(false);

  const toggle = () => {
    setIsOn(!isOn);
  };

  return { isOn, toggle };
};

export default useToggleButton;
