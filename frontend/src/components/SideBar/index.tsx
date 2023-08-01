import { useLocation } from 'react-router-dom';
import { Option } from '../../types';
import { Arrow, LabelContent, SideBarContainer, SideBarContent, SideBarLink } from './style';

interface SideBarProps {
  width: number;
  height: number;
  options: Option[];
}

const SideBar = ({ width, height, options }: SideBarProps) => {
  const current = useLocation().pathname;

  return (
    <SideBarContainer $width={width} $height={height}>
      {options.map(({ key, value }) => {
        return (
          <SideBarContent key={key} $isSelected={value === current}>
            <SideBarLink to={value}>
              <LabelContent
                $isSelected={value === current}
                $width={width}
                $height={height / options.length}
              >
                {key}
                <Arrow />
              </LabelContent>
            </SideBarLink>
          </SideBarContent>
        );
      })}
    </SideBarContainer>
  );
};

export default SideBar;
