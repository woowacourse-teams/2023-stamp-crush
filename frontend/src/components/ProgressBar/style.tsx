import styled from 'styled-components';

export const Bar = styled.div<{ $barWidth: string }>`
  width: ${({ $barWidth }) => $barWidth};
  height: 12px;
  background-color: #eee;
  border-radius: 10px 10px 0 0;
  /* box-shadow: 1px 1px 1px 1px #e0e0e0; */
`;

export const Progress = styled.div<{ $width: number; $color: string }>`
  width: ${(props) => `${props.$width}%`};
  height: 12px;
  background: ${(props) => `${props.$color}`};
  border-radius: 10px 10px 10px 0;
  transition: all 0.4s;
`;
