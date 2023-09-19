import CheckIconSVG from '../../../assets/check.svg';
import { CheckItemContainer } from './style';

interface CheckItemProps {
  text: string;
}

const CheckItem = ({ text }: CheckItemProps) => {
  return (
    <CheckItemContainer>
      <CheckIconSVG width={18} height={18} />
      {text}
    </CheckItemContainer>
  );
};

export default CheckItem;
