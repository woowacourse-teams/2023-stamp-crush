import CheckIconSVG from '../../../../assets/check.svg';
import { CheckItemContainer } from '../style';

interface CheckItemProps {
  text: string;
}

const CheckItem = ({ text }: CheckItemProps) => {
  return (
    <CheckItemContainer>
      <CheckIconSVG width={24} height={24} />
      {text}
    </CheckItemContainer>
  );
};

export default CheckItem;
