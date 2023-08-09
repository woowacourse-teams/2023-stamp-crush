import { useLocation } from 'react-router-dom';
import { Logo } from '../../assets';
import { Option } from '../../types';
import {
  LabelContent,
  LogoHeader,
  LogoImg,
  LogoImgWrapper,
  SideBarContainer,
  SideBarContent,
  SideBarLink,
} from './style';

interface SideBarProps {
  width: number;
  height: number;
  options: Option[];
}

const SideBar = ({ width, height, options }: SideBarProps) => {
  const current = useLocation().pathname;

  return (
    <>
      <LogoHeader>
        <LogoImgWrapper>
          <LogoImg src={Logo} alt="스탬프크러쉬 로고" />
        </LogoImgWrapper>
      </LogoHeader>
      <SideBarContainer $width={width} $height={height}>
        {options.map(({ key, value }) => (
          <SideBarContent key={key} $isSelected={value === current}>
            <SideBarLink to={value}>
              <LabelContent
                $isSelected={value === current}
                $width={width}
                $height={height / options.length}
              >
                {key}
              </LabelContent>
            </SideBarLink>
          </SideBarContent>
        ))}
      </SideBarContainer>
    </>
  );
};

export default SideBar;
