import { MouseEvent, useState, Dispatch, SetStateAction } from 'react';
import { Option } from '../../types/utils';
import { BaseSelectBox, LabelContent, SelectBoxWrapper, SelectContent } from './style';

interface SelectBoxProps {
  width?: number;
  options: Option[];
  checkedOption: Option;
  setCheckedOption: Dispatch<SetStateAction<Option>>;
}

const SelectBox = ({ options, checkedOption, setCheckedOption, width = 110 }: SelectBoxProps) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const getOption = (value: string) => {
    return [...options.filter((option: Option) => option.value === value && option)][0];
  };

  const toggleExpandSelectBox = (e: MouseEvent<HTMLInputElement>) => {
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
