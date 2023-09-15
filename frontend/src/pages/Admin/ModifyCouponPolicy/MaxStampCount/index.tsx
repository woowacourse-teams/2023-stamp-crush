import { Spacing } from '../../../../style/layout/common';
import Text from '../../../../components/Text';
import SelectBox from '../../../../components/SelectBox';
import { STAMP_COUNT_CUSTOM_OPTIONS, STAMP_COUNT_OPTIONS } from '../../../../constants';
import { Dispatch, SetStateAction } from 'react';
import { CouponCreated } from '../../../../types/domain/coupon';
import { Option } from '../../../../types/utils';

interface MaxStampCountProps {
  createdType: CouponCreated;
  stampCount: Option;
  setStampCount: Dispatch<SetStateAction<Option>>;
}

const MaxStampCount = ({ createdType, stampCount, setStampCount }: MaxStampCountProps) => {
  const options = createdType === 'template' ? STAMP_COUNT_OPTIONS : STAMP_COUNT_CUSTOM_OPTIONS;

  return (
    <>
      <Text variant="subTitle">step2. 쿠폰의 최대 스탬프 갯수를 선택해주세요.</Text>
      <Spacing $size={32} />
      <SelectBox options={options} checkedOption={stampCount} setCheckedOption={setStampCount} />
    </>
  );
};

export default MaxStampCount;
