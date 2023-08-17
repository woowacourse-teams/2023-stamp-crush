import { ChangeEvent } from 'react';
import { Option } from '../../types';
import { LabelContent, TabBarContainer, TabBarInput, TabBarItem, TabBarLabel } from './style';

interface TabBarProps {
  name: string;
  options: Option[];
  width: number;
  height: number;
  selectedValue: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}

const TabBar = ({ name, options, height, width, selectedValue, onChange }: TabBarProps) => {
  return (
    <TabBarContainer $width={width} $height={height}>
      {options.map(({ key, value }) => (
        <TabBarItem key={key} $isSelect={selectedValue === value}>
          <TabBarLabel>
            <LabelContent
              $isSelect={selectedValue === value}
              $width={width / options.length}
              $height={height}
            >
              {value}
            </LabelContent>
            <TabBarInput type="radio" name={name} value={value} onChange={onChange} />
          </TabBarLabel>
        </TabBarItem>
      ))}
    </TabBarContainer>
  );
};

export default TabBar;
