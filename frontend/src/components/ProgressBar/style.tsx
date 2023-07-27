import styled from 'styled-components';

type ProgressProps = {
  $width: number;
};

export const Bar = styled.div`
  width: 80%;
  height: 10px;
  background-color: #ddd;
  border-radius: 8px;
`;

export const Progress = styled.div<ProgressProps>`
  width: ${(props) => `${props.$width}%`};
  height: 10px;
  background-color: skyblue;
  border-radius: 8px;
  transition: all 0.4s;
`;
