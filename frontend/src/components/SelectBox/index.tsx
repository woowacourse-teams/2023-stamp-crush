import { MouseEventHandler, useState, Dispatch, SetStateAction } from 'react';

import { BaseSelectBox, LabelContent, SelectBoxWrapper, SelectContent } from './SelectBox.style';

export type SelectBoxOption = {
  key: string;
  value: string;
};

interface SelectBoxProps {
  options: SelectBoxOption[];
  checkedOption: SelectBoxOption;
  setCheckedOption: Dispatch<SetStateAction<SelectBoxOption>>;
}

const SelectBox = ({ options, checkedOption, setCheckedOption }: SelectBoxProps) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const getOption = (value: string) => {
    return [...options.filter((option: SelectBoxOption) => option.value === value && option)][0];
  };

  const toggleExpandSelectBox: MouseEventHandler<HTMLInputElement> = (event) => {
    event.preventDefault();

    setIsExpanded(!isExpanded);

    if (event.target instanceof HTMLLabelElement) {
      event.target.textContent && setCheckedOption(getOption(event.target.textContent));
    }

    if (event.target instanceof HTMLInputElement) {
      setCheckedOption(getOption(event.target.value));
    }
  };

  return (
    <SelectBoxWrapper>
      <BaseSelectBox
        $expanded={isExpanded}
        $minWidth={130}
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
