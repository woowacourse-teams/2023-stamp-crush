import { ArrowIconWrapper, HeaderContainer } from './style';
import { BiArrowBack } from '@react-icons/all-files/bi/BiArrowBack';

interface SubHeaderProps {
  title: string;
  onClickBack?: () => void;
}

const SubHeader = ({ title, onClickBack }: SubHeaderProps) => {
  return (
    <HeaderContainer>
      {onClickBack && (
        <ArrowIconWrapper onClick={onClickBack}>
          <BiArrowBack size={24} />
        </ArrowIconWrapper>
      )}
      {title}
    </HeaderContainer>
  );
};

export default SubHeader;
