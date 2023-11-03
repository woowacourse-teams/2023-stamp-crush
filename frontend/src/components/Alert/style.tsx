import { styled } from 'styled-components';
import { Z_INDEX } from '../../constants/magicNumber';

export const AlertContainer = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 400px;
  width: 70%;
  position: absolute;
  top: 50%;
  left: 50%;
  border-radius: 10px;
  z-index: ${Z_INDEX.highest + Z_INDEX.above};
  transform: translate(-50%, -50%);
`;

export const BackDrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 120vh;
  z-index: ${Z_INDEX.highest};
  background-color: rgba(0, 0, 0, 0.3);
`;

export const TextContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px;
  gap: 20px;
  background: #e8e8e8;
  width: 100%;
  height: 80%;
  border-radius: 10px 10px 0 0;
  text-align: center;
  white-space: pre-line;
  line-height: 24px;

  & > img {
    width: 60px;
    height: 60px;
    color: #eee;
  }
`;

export const OptionContainer = styled.div`
  display: flex;
  border-radius: 0 0 10px 10px;

  background: white;
  overflow: hidden;
`;

export const OptionWrapper = styled.button<{ $option: string }>`
  width: 100%;
  height: 50px;
  color: ${(props) => (props.$option === 'left' ? '#888' : '#424242')};

  &:active {
    background: #eeeeee;
    opacity: 50%;
    transform: scale(0.95);
  }
`;
