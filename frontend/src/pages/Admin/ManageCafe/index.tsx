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
  TextArea,
  Wrapper,
} from './style';
import TimeRangePicker from './TimeRangePicker';
import { ChangeEventHandler, FormEventHandler, useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
} from '../CustomCouponDesign/style';
import useUploadImage from '../../../hooks/useUploadImage';
import { FaRegClock, FaPhoneAlt } from 'react-icons/fa';
import { FaLocationDot } from 'react-icons/fa6';
import { useMutation, useQuery } from '@tanstack/react-query';
import { getCafe } from '../../../api/get';
import { isEmptyData, parsePhoneNumber, parseTime } from '../../../utils';
import { CafeInfoBody, patchCafeInfo } from '../../../api/patch';
import { ROUTER_PATH } from '../../../constants';

export interface Time {
  hour: string;
  minute: string;
}

const ManageCafe = () => {
  const navigate = useNavigate();
  const [cafeImage, uploadCafeImage] = useUploadImage();
  const [phoneNumber, setPhoneNumber] = useState('');
  const [introduction, setIntroduction] = useState('내용을 입력해주세요');
  const [openTime, setOpenTime] = useState<Time>({ hour: '10', minute: '00' });
  const [closeTime, setCloseTime] = useState<Time>({ hour: '18', minute: '00' });

  const { data: cafe } = useQuery(['cafe'], () => getCafe());

  const cafeInfo = useMemo(() => {
    return cafe ? cafe?.[0] : {};
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
  }, [cafeInfo]);

  const { mutate, isLoading, isError } = useMutation(
    (body: CafeInfoBody) => patchCafeInfo(cafe?.[0].id, body),
    {
      onSuccess: () => {
        navigate(ROUTER_PATH.customerList);
      },
      onError: () => {
        throw new Error('카페 정보 등록에 실패했습니다.');
      },
    },
  );

  const inputPhoneNumber: ChangeEventHandler<HTMLInputElement> = (e) => {
    setPhoneNumber(e.target.value);
  };

  const inputIntroduction: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
    setIntroduction(e.target.value);
  };

  const validateTimeRange = () => {
    return parseTime(openTime) < parseTime(closeTime);
  };

  // TODO: 시간이 빈값인 케이스 대처 x
  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    if (!validateTimeRange()) {
      alert('올바른 영업 시간을 입력해주세요');
      return;
    }

    const cafeInfoBody: CafeInfoBody = {
      openTime: parseTime(openTime),
      closeTime: parseTime(closeTime),
      telephoneNumber: phoneNumber,
      cafeImageUrl: cafeImage,
      introduction: introduction === '내용을 입력해주세요' ? '' : introduction,
    };

    mutate(cafeInfoBody);
  };

  return (
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
            maxLength={phoneNumber.startsWith('02') ? 10 : 11}
            onChange={inputPhoneNumber}
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
          <TextArea rows={8} cols={50} onChange={inputIntroduction} />
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
  );
};

export default ManageCafe;
