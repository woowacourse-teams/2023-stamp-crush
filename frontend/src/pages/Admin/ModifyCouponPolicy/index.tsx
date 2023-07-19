import { useRef, useState } from 'react';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import { ModifyCouponPolicyContainer, NextButtonWrapper, SelectBoxWrapper } from './style';
import RadioInputs from './RadioInputs';
import { useNavigate } from 'react-router-dom';
import { Spacing, Title, SectionTitle, Text } from '../../../style/layout/common';
import SelectBox from '../../../components/SelectBox';

const ParagraphSpacing = () => <Spacing $size={8} />;

const SectionSpacing = () => <Spacing $size={40} />;

const STAMP_COUNT_OPTIONS = [
  {
    key: 'eight',
    value: '8개',
  },
  {
    key: 'nine',
    value: '9개',
  },
  {
    key: 'ten',
    value: '10개',
  },
];

const EXPIRE_DATE_OPTIONS = [
  {
    key: 'six-month',
    value: '6개월',
  },
  {
    key: 'twelve-month',
    value: '12개월',
  },
  {
    key: 'infinity',
    value: '없음',
  },
];

const ModifyCouponPolicy = () => {
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
  const navigate = useNavigate();

  const navigateNextPage = () => {
    navigate('/admin/modify-coupon-policy/2', {
      state: {
        createdType,
        rewardInputRef: rewardInputRef.current?.value,
        expireSelect: expireSelect.value,
        stampCount: stampCount.value,
      },
    });
  };

  return (
    <ModifyCouponPolicyContainer>
      <Title>쿠폰 제작 및 변경</Title>
      <SectionSpacing />
      <SectionTitle>어떻게 제작하시겠어요?</SectionTitle>
      <ParagraphSpacing />
      <RadioInputs setValue={setCreatedType} />
      <SectionSpacing />
      <SectionTitle>목표 스탬프 갯수</SectionTitle>
      <ParagraphSpacing />
      <Text>리워드를 지급할 스탬프 갯수를 작성해주세요.</Text>
      <SelectBoxWrapper>
        <SelectBox
          options={STAMP_COUNT_OPTIONS}
          checkedOption={stampCount}
          setCheckedOption={setStampCount}
        />
      </SelectBoxWrapper>
      {/* TODO: placeholder가 적용되지 않는 오류가 있음. */}
      <Input
        id="create-coupon-reward-input"
        label="리워드 명"
        type="text"
        placeholder="어떤 상품의 리워드를 전달할지 작성해주세요. ex) 아메리카노"
        required={true}
        width={400}
        ref={rewardInputRef}
      />
      <SectionSpacing />
      <SectionTitle>쿠폰 유효기간</SectionTitle>
      <ParagraphSpacing />
      <Text>
        고객이 가지게 될 쿠폰의 유효기간입니다. 유효기간이 지나면 해당 쿠폰의 스탬프가 모두
        소멸됩니다.
      </Text>
      <SelectBoxWrapper>
        <SelectBox
          options={EXPIRE_DATE_OPTIONS}
          checkedOption={expireSelect}
          setCheckedOption={setExpireSelect}
        />
      </SelectBoxWrapper>
      <SectionSpacing />
      <NextButtonWrapper>
        <Button variant="secondary" size="medium" onClick={navigateNextPage}>
          다음으로
        </Button>
      </NextButtonWrapper>
    </ModifyCouponPolicyContainer>
  );
};

export default ModifyCouponPolicy;
