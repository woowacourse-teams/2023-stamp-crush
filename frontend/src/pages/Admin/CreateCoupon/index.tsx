import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import {
  CreateCouponContainer,
  NextButtonWrapper,
  Spacing,
  SubTitle,
  Text,
  Title,
} from './CreateCoupon.style';

const ParagraphSpacing = () => <Spacing $size={8} />;

const SectionSpacing = () => <Spacing $size={40} />;

const CreateCoupon = () => {
  return (
    <CreateCouponContainer>
      <Title>쿠폰 제작 및 변경</Title>
      <ParagraphSpacing />
      <SubTitle>어떻게 제작하시겠어요?</SubTitle>
      {/* Radio Buttons */}
      <SectionSpacing />
      <SubTitle>목표 스탬프 갯수</SubTitle>
      <ParagraphSpacing />
      <Text>리워드를 지급할 스탬프 갯수를 작성해주세요.</Text>
      <Input
        id="create-coupon-reward-input"
        label="리워드 명"
        type="text"
        placeholder="어떤 상품의 리워드를 전달할지 작성해주세요. ex) 아메리카노"
        required={true}
        width={400}
      />
      <SectionSpacing />
      <SubTitle>쿠폰 유효기간</SubTitle>
      <ParagraphSpacing />
      <Text>
        고객이 가지게 될 쿠폰의 유효기간입니다. 유효기간이 지나면 해당 쿠폰의 스탬프가 모두
        소멸됩니다.
      </Text>
      <SectionSpacing />
      <NextButtonWrapper>
        <Button variant="secondary" size="medium">
          다음으로
        </Button>
      </NextButtonWrapper>
    </CreateCouponContainer>
  );
};

export default CreateCoupon;
