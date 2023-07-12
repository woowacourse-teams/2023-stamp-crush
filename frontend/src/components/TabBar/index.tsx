import {
  LabelContent,
  TabBarContainer,
  TabBarInput,
  TabBarItem,
  TabBarLabel,
} from './TabBar.style';

interface TabBarOption {
  key: string;
  value: string;
}

interface TabBarProps {
  name: string;
  options: TabBarOption[];
  height: number;
  width: number;
  selectedValue: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
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
