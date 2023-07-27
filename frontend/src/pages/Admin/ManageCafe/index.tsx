import Text from '../../../components/Text';
import { Input } from '../../../components/Input';
import Button from '../../../components/Button';
import {
  ManageCafeForm,
  PageContainer,
  PreviewContainer,
  PreviewContentContainer,
  PreviewOverviewContainer,
  TextArea,
  Wrapper,
} from './style';
import TimeRangePicker from './TimeRangePicker';
import { ChangeEventHandler, FormEventHandler, useState } from 'react';
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

export interface Time {
  hour: string;
  minute: string;
}

const ManageCafe = () => {
  const navigate = useNavigate();
  const [cafeImage, uploadCafeImage] = useUploadImage();
  const [phoneNumber, setPhoneNumber] = useState('');
  const [introduction, setIntroduction] = useState('내용을 입력해주세요');
  const [startTime, setStartTime] = useState<Time>({ hour: '10', minute: '00' });
  const [endTime, setEndTime] = useState<Time>({ hour: '18', minute: '00' });

  const inputPhoneNumber: ChangeEventHandler<HTMLInputElement> = (e) => {
    setPhoneNumber(e.target.value);
  };

  const inputIntroduction: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
    setIntroduction(e.target.value);
  };

  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    console.log(cafeImage, startTime, endTime, introduction, phoneNumber);

    // navigate('/admin');
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
            onChange={inputPhoneNumber}
          />
        </Wrapper>
        <Wrapper>
          <Text>영업 시간(선택)</Text>
          <TimeRangePicker
            startTime={startTime}
            endTime={endTime}
            setStartTime={setStartTime}
            setEndTime={setEndTime}
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
          <PreviewImage src={cafeImage} $width={312} $height={594} $opacity={0.25} />
          <PreviewOverviewContainer>
            <Text variant="subTitle">cafeName</Text>
            <Text>{introduction}</Text>
          </PreviewOverviewContainer>
          <PreviewContentContainer>
            <Text>
              <FaRegClock size={25} />
              {`여는 시간 ${startTime.hour}:${startTime.minute}\n닫는 시간 ${endTime.hour}:${endTime.minute}`}
            </Text>
            <Text>
              <FaPhoneAlt size={25} />
              {phoneNumber}
            </Text>
            <Text>
              <FaLocationDot size={25} />
              주소
            </Text>
          </PreviewContentContainer>
        </PreviewImageWrapper>
      </PreviewContainer>
    </PageContainer>
  );
};

export default ManageCafe;
