import Text from '../../../components/Text';
import { Input } from '../../../components/Input';
import Button from '../../../components/Button';
import {
  ManageCafeForm,
  PageContainer,
  PreviewContainer,
  PreviewContentContainer,
  PreviewEmptyCouponImage,
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
import { getCafe } from '../../../api/get';
import { isEmptyData, parsePhoneNumber, parseTime } from '../../../utils';
import { patchCafeInfo } from '../../../api/patch';
import { ROUTER_PATH } from '../../../constants';
import { Cafe, Time } from '../../../types';
import { CafeInfoReqBody } from '../../../types/api';
import LoadingSpinner from '../../../components/LoadingSpinner';

const ManageCafe = () => {
  const navigate = useNavigate();
  const [cafeImage, uploadCafeImage] = useUploadImage();
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
  }, [cafeInfo]);

  const { mutate, isLoading, isError } = useMutation(patchCafeInfo, {
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

  // TODO: 시간이 빈값인 케이스 대처 x
  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    const cafeInfoBody: CafeInfoReqBody = {
      openTime: parseTime(openTime),
      closeTime: parseTime(closeTime),
      telephoneNumber: phoneNumber,
      // FIXME: 하드코딩 된 값 변경하기
      cafeImageUrl: cafeImage === '' ? 'https://picsum.photos/200/300' : cafeImage,
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
  if (status === 'loading') return <LoadingSpinner />;
  if (status === 'error') return <>에러가 발생했습니다.</>;

  return (
    <Suspense fallback={<LoadingSpinner />}>
      <PageContainer>
        <ManageCafeForm onSubmit={submitCafeInfo}>
          <Text variant="pageTitle">내 카페 관리</Text>
          <Wrapper>
            <Text>카페 대표 사진(선택)</Text>
            <ImageUpLoadInput
              id="cafe-image"
              type="file"
              accept="image/jpg,image/png,image/jpeg,image/gif"
              onChange={uploadCafeImage}
            />
            <ImageUpLoadInputLabel htmlFor={'cafe-image'}>이미지 업로드 +</ImageUpLoadInputLabel>
          </Wrapper>
          <Wrapper>
            <Text>매장 전화번호(선택)</Text>
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
            <Text>영업 시간(선택)</Text>
            <TimeRangePicker
              startTime={openTime}
              endTime={closeTime}
              setStartTime={setOpenTime}
              setEndTime={setCloseTime}
            />
          </Wrapper>
          <Wrapper>
            <Text>소개글</Text>
            <TextArea
              rows={8}
              cols={50}
              onChange={inputIntroduction}
              maxLength={150}
              value={introduction}
            />
            <RestrictionLabel
              $isExceed={introduction.length >= 150}
            >{`${introduction.length}/150`}</RestrictionLabel>
          </Wrapper>
          <Button type="submit" variant="primary" size="medium">
            저장하기
          </Button>
        </ManageCafeForm>
        <PreviewContainer>
          <Text variant="subTitle">미리보기</Text>
          <PreviewImageWrapper $width={312} $height={594}>
            <PreviewImage
              src={
                isEmptyData(cafeInfo.cafeImageUrl) || !isEmptyData(cafeImage)
                  ? cafeImage
                  : cafeInfo.cafeImageUrl
              }
              $width={312}
              $height={594}
              $opacity={0.25}
            />
            <PreviewEmptyCouponImage>쿠폰 뒷면 이미지가 들어갈 공간입니다.</PreviewEmptyCouponImage>
            <PreviewOverviewContainer>
              <Text variant="subTitle">{cafeInfo.name}</Text>
              <Text>{introduction}</Text>
            </PreviewOverviewContainer>
            <PreviewContentContainer>
              <Text>
                <FaRegClock size={25} />
                {`여는 시간 ${parseTime(openTime)}\n닫는 시간 ${parseTime(closeTime)}`}
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
    </Suspense>
  );
};

export default ManageCafe;
