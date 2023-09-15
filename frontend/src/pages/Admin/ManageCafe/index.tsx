import Text from '../../../components/Text';
import Button from '../../../components/Button';
import {
  ManageCafeForm,
  ManageCafeGridContainer,
  PageContainer,
  PreviewContainer,
  Wrapper,
} from './style';
import { useMemo } from 'react';
import { PreviewImageWrapper } from '../CouponDesign/CustomCouponDesign/style';
import { DEFAULT_CAFE } from '../../../constants';
import LoadingSpinner from '../../../components/LoadingSpinner';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import { Cafe } from '../../../types/domain/cafe';
import useGetCafe from './hooks/useGetCafe';
import usePatchCafeInfo from './hooks/usePatchCafeInfo';
import useManageCafe from './hooks/useManageCafe';
import CafeIntroduction from './components/CafeIntroduction';
import PreviewCafeImage from './components/PreviewCafeImage';
import PreviewCoupon from './components/PreviewCoupon';
import PreviewContent from './components/PreviewContent';
import CafeImageUpload from './components/CafeImageUpload';
import CafePhoneNumber from './components/CafePhoneNumber';
import CafeTimePicker from './components/CafeTimePicker';
import PreviewOverview from './components/PreviewOverview';

const ManageCafe = () => {
  const cafeId = useRedirectRegisterPage();

  const { data: cafe, status } = useGetCafe();

  const cafeInfo: Cafe = useMemo(() => {
    return cafe ? cafe?.cafes[0] : DEFAULT_CAFE;
  }, [cafe]);

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

  if (status === 'loading') return <LoadingSpinner />;
  if (status === 'error') return <>에러가 발생했습니다.</>;

  return (
    <ManageCafeGridContainer>
      <ManageCafeForm onSubmit={submitCafeInfo}>
        <Text variant="pageTitle">내 카페 관리</Text>
        <CafeImageUpload uploadImage={uploadImage} />
        <CafePhoneNumber phoneNumber={phoneNumber} inputPhoneNumber={inputPhoneNumber} />
        <CafeTimePicker
          openTime={openTime}
          closeTime={closeTime}
          setOpenTime={setOpenTime}
          setCloseTime={setCloseTime}
        />
        <Wrapper>
          <CafeIntroduction introduction={introduction} inputIntroduction={inputIntroduction} />
          <Button type="submit" variant="primary" size="medium">
            저장하기
          </Button>
        </Wrapper>
      </ManageCafeForm>
      <PreviewContainer>
        <Text variant="subTitle">미리보기</Text>
        <PreviewImageWrapper $width={312} $height={594}>
          <PreviewCafeImage cafeImageUrl={cafeImage} />
          <PreviewCoupon />
          <PreviewOverview cafeName={cafeInfo.name} introduction={introduction} />
          <PreviewContent
            openTime={openTime}
            closeTime={closeTime}
            phoneNumber={phoneNumber}
            cafeInfo={cafeInfo}
          />
        </PreviewImageWrapper>
      </PreviewContainer>
    </ManageCafeGridContainer>
  );
};

export default ManageCafe;
