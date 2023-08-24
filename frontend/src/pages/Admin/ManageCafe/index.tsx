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
  RestrictionLabel,
  StepTitle,
  TextArea,
  Wrapper,
} from './style';
import TimeRangePicker from './TimeRangePicker';
import { ChangeEvent, FormEventHandler, Suspense, useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
} from '../CouponDesign/CustomCouponDesign/style';
import useUploadImage from '../../../hooks/useUploadImage';
import { FaRegClock, FaPhoneAlt } from 'react-icons/fa';
import { AiOutlineUpload } from 'react-icons/ai';
import { FaLocationDot } from 'react-icons/fa6';
import { useMutation, useQuery } from '@tanstack/react-query';
import { getCafe, getCouponDesign } from '../../../api/get';
import { isEmptyData, parsePhoneNumber, parseTime } from '../../../utils';
import { patchCafeInfo } from '../../../api/patch';
import { INVALID_CAFE_ID, ROUTER_PATH } from '../../../constants';
import { Cafe, Time } from '../../../types';
import { CafeInfoReqBody } from '../../../types/api';
import LoadingSpinner from '../../../components/LoadingSpinner';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import defaultCafeImg from '../../../assets/default_cafe_bg.png';

const ManageCafe = () => {
  const cafeId = useRedirectRegisterPage();
  const navigate = useNavigate();
  const [cafeImage, uploadCafeImage, setCafeImage] = useUploadImage();
  const [phoneNumber, setPhoneNumber] = useState('');
  const [introduction, setIntroduction] = useState('');

  const [openTime, setOpenTime] = useState<Time>({ hour: '10', minute: '00' });
  const [closeTime, setCloseTime] = useState<Time>({ hour: '18', minute: '00' });

  const { data: cafe, status } = useQuery(['cafe'], async () => await getCafe());

  const cafeInfo: Cafe = useMemo(() => {
    return cafe
      ? cafe?.cafes[0]
      : // FIXME: 하드코딩된 빈 카페
        {
          id: 0,
          name: '',
          introduction: '',
          openTime: '',
          closeTime: '',
          telephoneNumber: '',
          cafeImageUrl: '',
          roadAddress: '',
          detailAddress: '',
        };
  }, [cafe]);

  const { data: couponDesignData, status: couponDesignStatus } = useQuery({
    queryKey: ['couponDesign'],
    queryFn: () => getCouponDesign({ params: { cafeId } }),
    enabled: cafeId !== INVALID_CAFE_ID,
  });

  const splitTime = (timeString: string) => {
    const [hour, minute] = timeString.split(':');

    return { hour, minute };
  };

  useEffect(() => {
    if (!(isEmptyData(cafeInfo.openTime) && isEmptyData(cafeInfo.closeTime))) {
      setOpenTime(splitTime(cafeInfo.openTime));
      setCloseTime(splitTime(cafeInfo.closeTime));
    }
    if (!isEmptyData(cafeInfo.telephoneNumber)) setPhoneNumber(cafeInfo.telephoneNumber);
    if (!isEmptyData(cafeInfo.introduction)) setIntroduction(cafeInfo.introduction);
    if (!isEmptyData(cafeInfo.cafeImageUrl)) setCafeImage(cafeInfo.cafeImageUrl);
  }, [cafeInfo]);

  const { mutate } = useMutation(patchCafeInfo, {
    onSuccess: () => {
      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      throw new Error('카페 정보 등록에 실패했습니다.');
    },
  });

  const inputPhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;

    const removeHyphenPhoneNumber = value.replace(/-/g, '');

    setPhoneNumber(removeHyphenPhoneNumber);
  };

  const inputIntroduction = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setIntroduction(e.target.value);
  };

  const uploadImage = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    uploadCafeImage(e);
  };

  // TODO: 시간이 빈값인 케이스 대처 x
  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    const cafeInfoBody: CafeInfoReqBody = {
      openTime: parseTime(openTime),
      closeTime: parseTime(closeTime),
      telephoneNumber: phoneNumber,
      // FIXME: 하드코딩 된 값 변경하기
      cafeImageUrl: cafeImage,
      introduction: introduction,
    };

    mutate({
      params: {
        cafeId: cafeInfo.id,
      },
      body: cafeInfoBody,
    });
  };

  // TODO: 로딩, 에러 컴포넌트 만들기
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
          <StepTitle>step4. 내 카페를 소개할 간단한 문장을 작성해주세요.</StepTitle>
          <TextArea
            rows={8}
            cols={50}
            onChange={inputIntroduction}
            maxLength={150}
            value={introduction}
          />
          <RestrictionLabel $isExceed={!introduction ? false : introduction.length >= 150}>
            {!introduction ? '0/150' : `${introduction.length}/150`}
          </RestrictionLabel>
        </Wrapper>
        <Button type="submit" variant="primary" size="medium">
          저장하기
        </Button>
      </ManageCafeForm>
      <PreviewContainer>
        <Text variant="subTitle">미리보기</Text>
        <PreviewImageWrapper $width={312} $height={594}>
          <PreviewImage
            src={!cafeImage ? defaultCafeImg : cafeImage}
            $width={312}
            $height={594}
            $opacity={0.25}
          />
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
