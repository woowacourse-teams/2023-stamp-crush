import { MouseEventHandler, useState, Dispatch, SetStateAction } from 'react';
import { BaseSelectBox, LabelContent, SelectBoxWrapper, SelectContent } from './style';

export type SelectBoxOption = {
  key: string;
  value: string;
};

interface SelectBoxProps {
  width?: number;
  options: SelectBoxOption[];
  checkedOption: SelectBoxOption;
  setCheckedOption: Dispatch<SetStateAction<SelectBoxOption>>;
}

const SelectBox = ({ options, checkedOption, setCheckedOption, width = 110 }: SelectBoxProps) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const getOption = (value: string) => {
    return [...options.filter((option: SelectBoxOption) => option.value === value && option)][0];
  };

  const toggleExpandSelectBox: MouseEventHandler<HTMLInputElement> = (e) => {
    e.preventDefault();

    setIsExpanded(!isExpanded);

    if (e.target instanceof HTMLLabelElement) {
      e.target.textContent && setCheckedOption(getOption(e.target.textContent));
    }

    if (e.target instanceof HTMLInputElement) {
      setCheckedOption(getOption(e.target.value));
    }
  };

  return (
    <SelectBoxWrapper>
      <BaseSelectBox
        $expanded={isExpanded}
        $minWidth={width}
        $minHeight={32}
        onClick={toggleExpandSelectBox}
      >
        {options.map((option) => (
          <>
            <SelectContent
              id={option.key}
              key={option.key}
              type="radio"
              value={option.value}
              checked={checkedOption.value === option.value}
            />
            <LabelContent htmlFor={option.key}>{option.value}</LabelContent>
          </>
        ))}
      </BaseSelectBox>
    </SelectBoxWrapper>
  );
};

export default SelectBox;
