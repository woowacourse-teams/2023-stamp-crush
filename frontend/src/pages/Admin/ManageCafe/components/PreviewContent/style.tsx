import { styled } from 'styled-components';

export const PreviewContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  bottom: 15%;
  left: 30px;
  width: 240px;

  gap: 20px;

  :nth-child(n) {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    gap: 10px;
    overflow: hidden;
  }
`;
