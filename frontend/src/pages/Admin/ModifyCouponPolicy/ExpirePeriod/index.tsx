import { Spacing } from '../../../../style/layout/common';
import Text from '../../../../components/Text';
import SelectBox from '../../../../components/SelectBox';
import { EXPIRE_DATE_OPTIONS } from '../../../../constants';
import { Dispatch, SetStateAction } from 'react';
import { WarningText } from './style';
import { AiFillWarning } from 'react-icons/ai';
import { Option } from '../../../../types/utils';

interface ExpirePeriodProps {
  expirePeriod: Option;
  setExpirePeriod: Dispatch<SetStateAction<Option>>;
}

const ExpirePeriod = ({ expirePeriod, setExpirePeriod }: ExpirePeriodProps) => {
  return (
    <>
      <Text variant="subTitle">step4. 쿠폰의 유효기간을 설정해주세요.</Text>
      <Spacing $size={16} />
      <WarningText>
        <AiFillWarning /> 유효기간이 지나면 해당 쿠폰의 스탬프가 모두 소멸됩니다.
      </WarningText>
      <Spacing $size={32} />
      <SelectBox
        options={EXPIRE_DATE_OPTIONS}
        checkedOption={expirePeriod}
        setCheckedOption={setExpirePeriod}
      />
    </>
  );
};

export default ExpirePeriod;
