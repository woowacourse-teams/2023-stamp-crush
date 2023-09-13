import { styled } from 'styled-components';

export const PreviewContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  bottom: 80px;
  left: 50px;
  width: 200px;
  height: 100px;
  gap: 20px;

  :nth-child(n) {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    gap: 10px;
  }
`;
