import styled from 'styled-components';

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

interface TabBarSelect {
  $isSelect: boolean;
}

interface TabBarWidth {
  $height: number;
  $width: number;
}

type TabBarContentProps = TabBarSelect & TabBarWidth;

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

const TabBarContainer = styled.div<TabBarWidth>`
  display: flex;
  width: ${(props) => `${props.$width}px`};
  position: relative;
  height: ${(props) => `${props.$height}px`};
`;

const TabBarLabel = styled.label`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

const LabelContent = styled.span<TabBarContentProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.4s ease;
  color: ${(props) => (props.$isSelect ? 'black' : '#eee')};
  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};
`;

const TabBarItem = styled.div<TabBarSelect>`
  transition: all 0.4s ease;
  border-bottom: 1px solid ${(props) => (props.$isSelect ? 'black' : '#eee')};
`;

const TabBarInput = styled.input`
  display: none;
`;

export default TabBar;
