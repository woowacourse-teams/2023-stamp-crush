import ReactDOM from 'react-dom';
import { AlertContainer, BackDrop, OptionContainer, OptionWrapper, TextContainer } from './style';
import { CiCircleAlert } from '../../assets';

export interface AlertProps {
  text: string;
  rightOption: string;
  leftOption?: string;
  onClickRight: () => void;
  onClickLeft?: () => void;
}

const Alert = ({ text, rightOption, leftOption, onClickLeft, onClickRight }: AlertProps) => {
  return ReactDOM.createPortal(
    <>
      <BackDrop onClick={onClickLeft} />
      <AlertContainer role="dialog">
        <TextContainer>
          <CiCircleAlert width={60} height={60} color={'#6e6e6e'} />
          {text}
        </TextContainer>
        <OptionContainer>
          {leftOption && (
            <OptionWrapper $option={'left'} onClick={onClickLeft}>
              {leftOption}
            </OptionWrapper>
          )}
          <OptionWrapper $option={'right'} onClick={onClickRight}>
            {rightOption}
          </OptionWrapper>
        </OptionContainer>
      </AlertContainer>
    </>,
    document.querySelector('body') as HTMLBodyElement,
  );
};

export default Alert;
