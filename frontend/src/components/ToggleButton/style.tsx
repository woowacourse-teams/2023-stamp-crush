import styled from 'styled-components';

export const Container = styled.button<{ $isOn: boolean }>`
  display: flex;
  align-items: center;
  width: 50px;
  height: 24px;
  position: relative;
  border-radius: 30px;
  background: gray;
  opacity: 50%;
  padding: 4px;
`;

export const ToggleSwitch = styled.span<{ $isOn: boolean }>`
  width: 18px;
  height: 18px;

  position: absolute;
  right: ${({ $isOn }) => ($isOn ? '4px' : '28px')};
  border-radius: 50%;
  background-color: white;
  opacity: ${({ $isOn }) => ($isOn ? '100%' : '50%')};
  transition: right 0.2s ease-in;
`;
