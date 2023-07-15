import { MouseEventHandler, useState, Dispatch, SetStateAction } from 'react';
import { BaseSelectBox, LabelContent, SelectContent } from './SelectBox.style';

export type SelectBoxOption = {
  key: string;
  value: string;
};

interface SelectBoxProps {
  options: SelectBoxOption[];
  checkedOption: string;
  setCheckedOption: Dispatch<SetStateAction<string>>;
}

const SelectBox = ({ options, checkedOption, setCheckedOption }: SelectBoxProps) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const toggleExpandSelectBox: MouseEventHandler<HTMLInputElement> = (event) => {
    event.preventDefault();

    setIsExpanded(!isExpanded);

    if (event.target instanceof HTMLLabelElement) {
      event.target.textContent && setCheckedOption(event.target.textContent);
    }

    if (event.target instanceof HTMLInputElement) {
      setCheckedOption(event.target.value);
    }
  };

  return (
    <BaseSelectBox
      $expanded={isExpanded}
      $minWidth={120}
      $minHeight={32}
      onClick={toggleExpandSelectBox}
    >
      {options.map(({ key, value }) => (
        <>
          <SelectContent
            key={key}
            type="radio"
            value={checkedOption}
            checked={checkedOption === value}
            id={key}
          />
          <LabelContent htmlFor={key}>{value}</LabelContent>
        </>
      ))}
    </BaseSelectBox>
  );
};

export default SelectBox;
