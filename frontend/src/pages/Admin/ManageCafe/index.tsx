import Text from '../../../components/Text';
import { Input } from '../../../components/Input';
import Button from '../../../components/Button';
import {
  ManageCafeForm,
  PageContainer,
  PreviewBackImage,
  PreviewContainer,
  PreviewContentContainer,
  PreviewCouponBackImage,
  PreviewOverviewContainer,
  StepTitle,
  Wrapper,
} from './style';
import TimeRangePicker from './TimeRangePicker';
import { useMemo } from 'react';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
} from '../CouponDesign/CustomCouponDesign/style';
import { FaRegClock, FaPhoneAlt } from 'react-icons/fa';
import { AiOutlineUpload } from 'react-icons/ai';
import { FaLocationDot } from 'react-icons/fa6';
import { parsePhoneNumber, parseTime } from '../../../utils';
import { DEFAULT_CAFE } from '../../../constants';
import LoadingSpinner from '../../../components/LoadingSpinner';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import { Cafe } from '../../../types/domain/cafe';
import useGetCafe from './hooks/useGetCafe';
import useGetCouponDesign from './hooks/useGetCouponDesign';
import usePatchCafeInfo from './hooks/usePatchCafeInfo';
import useManageCafe from './hooks/useManageCafe';
import CafeIntroduction from './components/CafeIntroduction';
import PreviewCafeImage from './components/PreviewCafeImage';

const ManageCafe = () => {
  const cafeId = useRedirectRegisterPage();

  const { data: cafe, status } = useGetCafe();

  const cafeInfo: Cafe = useMemo(() => {
    return cafe ? cafe?.cafes[0] : DEFAULT_CAFE;
  }, [cafe]);

  const { data: couponDesignData, status: couponDesignStatus } = useGetCouponDesign(cafeId);

  const { mutate } = usePatchCafeInfo();

  const {
    submitCafeInfo,
    uploadImage,
    inputPhoneNumber,
    inputIntroduction,
    openTime,
    setOpenTime,
    closeTime,
    setCloseTime,
    phoneNumber,
    introduction,
    cafeImage,
  } = useManageCafe(cafeId, cafeInfo, mutate);

  if (status === 'loading' || couponDesignStatus === 'loading') return <LoadingSpinner />;
  if (status === 'error' || couponDesignStatus === 'error') return <>에러가 발생했습니다.</>;

  return (
    <PageContainer>
      <ManageCafeForm onSubmit={submitCafeInfo}>
        <Text variant="pageTitle">내 카페 관리</Text>
        <Wrapper>
          <StepTitle>step1. 내 카페를 대표하는 내부사진을 업로드해주세요.</StepTitle>
          <ImageUpLoadInput
            id="cafe-image"
            type="file"
            accept="image/jpg,image/png,image/jpeg,image/gif"
            onChange={uploadImage}
          />
          <ImageUpLoadInputLabel htmlFor={'cafe-image'}>
            <AiOutlineUpload />
            Upload the file
          </ImageUpLoadInputLabel>
        </Wrapper>
        <Wrapper>
          <StepTitle>step2. 내 카페의 매장 전화번호를 알려주세요.</StepTitle>
          <Input
            id="phone-number-input"
            placeholder="전화번호를 입력해주세요"
            maxLength={phoneNumber.startsWith('02') ? 12 : 13}
            pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
            onChange={inputPhoneNumber}
            value={parsePhoneNumber(phoneNumber)}
          />
        </Wrapper>
        <Wrapper>
          <StepTitle>step3. 내 카페의 영업 시간을 알려주세요.</StepTitle>
          <TimeRangePicker
            startTime={openTime}
            endTime={closeTime}
            setStartTime={setOpenTime}
            setEndTime={setCloseTime}
          />
        </Wrapper>
        <Wrapper>
          <CafeIntroduction introduction={introduction} inputIntroduction={inputIntroduction} />
        </Wrapper>
        <Button type="submit" variant="primary" size="medium">
          저장하기
        </Button>
      </ManageCafeForm>
      <PreviewContainer>
        <Text variant="subTitle">미리보기</Text>
        <PreviewImageWrapper $width={312} $height={594}>
          <PreviewCafeImage cafeImage={cafeImage} />
          <PreviewCouponBackImage>
            {couponDesignData.backImageUrl !== '' ? (
              <PreviewBackImage src={couponDesignData.backImageUrl} />
            ) : (
              <p>쿠폰 뒷면 이미지가 들어갈 공간입니다.</p>
            )}
          </PreviewCouponBackImage>
          <PreviewOverviewContainer>
            <Text variant="subTitle">{cafeInfo.name}</Text>
            <Text>{introduction}</Text>
          </PreviewOverviewContainer>
          <PreviewContentContainer>
            <Text>
              <FaRegClock size={25} />
              {`${parseTime(openTime)} - ${parseTime(closeTime)}`}
            </Text>
            <Text>
              <FaPhoneAlt size={25} />
              {parsePhoneNumber(phoneNumber)}
            </Text>
            <Text>
              <FaLocationDot size={25} />
              {cafeInfo.roadAddress + ' ' + cafeInfo.detailAddress}
            </Text>
          </PreviewContentContainer>
        </PreviewImageWrapper>
      </PreviewContainer>
    </PageContainer>
  );
};

export default ManageCafe;
