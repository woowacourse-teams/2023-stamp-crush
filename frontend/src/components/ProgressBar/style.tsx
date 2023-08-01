import styled from 'styled-components';

export const Bar = styled.div`
  width: 80%;
  height: 12px;
  background-color: #ddd;
  border-radius: 8px;
  box-shadow: 1px 1px 1px 1px #e0e0e0;
`;

export const Progress = styled.div<{ $width: number; $color: string }>`
  width: ${(props) => `${props.$width}%`};
  height: 12px;
  background: ${(props) => `${props.$color}`};
  border-radius: 8px;
  transition: all 0.4s;
  box-shadow: 1px 1px 1px 1px #e0e0e0;
`;
