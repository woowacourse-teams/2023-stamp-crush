import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import { NextButtonWrapper } from './style';
import { Spacing } from '../../../style/layout/common';
import SelectBox from '../../../components/SelectBox';
import Text from '../../../components/Text';
import { EXPIRE_DATE_OPTIONS, ROUTER_PATH, STAMP_COUNT_OPTIONS } from '../../../constants';
import RadioInputs from './RadioInput';

const ModifyCouponPolicy = () => {
  const navigate = useNavigate();
  const [createdType, setCreatedType] = useState('');
  const rewardInputRef = useRef<HTMLInputElement>(null);
  const [expireSelect, setExpireSelect] = useState({
    key: EXPIRE_DATE_OPTIONS[0].key,
    value: EXPIRE_DATE_OPTIONS[0].value,
  });
  const [stampCount, setStampCount] = useState({
    key: STAMP_COUNT_OPTIONS[0].key,
    value: STAMP_COUNT_OPTIONS[0].value,
  });

  const navigateNextPage = () => {
    if (!createdType || !rewardInputRef.current?.value || !expireSelect || !stampCount) {
      alert('모두 작성해주세요.');
      return;
    }

    navigate(ROUTER_PATH.customCouponDesign, {
      state: {
        createdType,
        reward: rewardInputRef.current?.value,
        expirePeriod: expireSelect.value,
        stampCount: stampCount.value,
      },
    });
  };

  return (
    <>
      <Spacing $size={40} />
      <Text variant="pageTitle">쿠폰 제작 및 변경</Text>
      <Spacing $size={40} />
      <Text variant="subTitle">어떻게 제작하시겠어요?</Text>
      <Spacing $size={8} />
      <RadioInputs setValue={setCreatedType} />
      <Spacing $size={40} />
      <Text variant="subTitle">목표 스탬프 갯수</Text>
      <Spacing $size={8} />
      <Text>리워드를 지급할 스탬프 갯수를 작성해주세요.</Text>
      <Spacing $size={16} />
      <SelectBox
        options={STAMP_COUNT_OPTIONS}
        checkedOption={stampCount}
        setCheckedOption={setStampCount}
      />
      <Spacing $size={40} />
      <Input
        id="create-coupon-reward-input"
        label="리워드 명"
        type="text"
        placeholder="어떤 상품의 리워드를 전달할지 작성해주세요. ex) 아메리카노"
        required={true}
        width={400}
        ref={rewardInputRef}
      />
      <Spacing $size={40} />
      <Text variant="subTitle">쿠폰 유효기간</Text>
      <Spacing $size={8} />
      <Text>
        고객이 가지게 될 쿠폰의 유효기간입니다. 유효기간이 지나면 해당 쿠폰의 스탬프가 모두
        소멸됩니다.
      </Text>
      <Spacing $size={8} />
      <SelectBox
        options={EXPIRE_DATE_OPTIONS}
        checkedOption={expireSelect}
        setCheckedOption={setExpireSelect}
      />
      <Spacing $size={40} />
      <NextButtonWrapper>
        <Button variant="secondary" size="medium" onClick={navigateNextPage}>
          다음으로
        </Button>
      </NextButtonWrapper>
    </>
  );
};

export default ModifyCouponPolicy;
