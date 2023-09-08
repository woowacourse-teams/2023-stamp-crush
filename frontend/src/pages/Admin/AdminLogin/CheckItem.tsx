import CheckIconSrc from '../../../assets/check.svg';
import { CheckIcon, CheckItemContainer } from './style';

interface CheckItemProps {
  text: string;
}

const CheckItem = ({ text }: CheckItemProps) => {
  return (
    <CheckItemContainer>
      <CheckIcon src={CheckIconSrc} />
      {text}
    </CheckItemContainer>
  );
};

export default CheckItem;
