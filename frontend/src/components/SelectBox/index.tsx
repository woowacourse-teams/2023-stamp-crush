import { MouseEventHandler, useState } from 'react';
import { BaseSelectBox, LabelContent, SelectContent } from './SelectBox.style';

export type SelectBoxOption = {
  key: string;
  value: string;
};

interface SelectBoxProps {
  options: SelectBoxOption[];
}

const SelectBox = ({ options }: SelectBoxProps) => {
  const [checkedOption, setCheckedOption] = useState(options[0].value);
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
      className={isExpanded ? 'expanded' : ''}
      $minWidth={120}
      $minHeight={32}
      onClick={toggleExpandSelectBox}
    >
      {options.map(({ key, value }) => (
        <>
          <SelectContent
            key={key}
            type="radio"
            name="sortType"
            value={value}
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
