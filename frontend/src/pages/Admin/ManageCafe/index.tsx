import Text from '../../../components/Text';
import Button from '../../../components/Button';
import {
  ManageCafeForm,
  ManageCafeGridContainer,
  PreviewContainer,
  SkeletonHeader,
  SkeletonPreview,
  Wrapper,
} from './style';
import { useEffect, useMemo } from 'react';
import { PreviewImageWrapper } from '../CustomCouponDesign/style';
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
import LoadingSpinner from '../EarnStamp/components/LoadingSpinner';
import Alert from '../../../components/Alert';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../constants/routerPath';
import useModal from '../../../hooks/useModal';

const DEFAULT_CAFE = {
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

const ManageCafe = () => {
  const cafeId = useRedirectRegisterPage();
  const navigate = useNavigate();
  const { data: cafe, status } = useGetCafe();
  const { isOpen, openModal, closeModal } = useModal();

  const cafeInfo: Cafe = useMemo(() => {
    return cafe ? cafe?.cafes[0] : DEFAULT_CAFE;
  }, [cafe]);

  const { mutate, status: patchInfoStatus } = usePatchCafeInfo();

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

  useEffect(() => {
    if (patchInfoStatus === 'error') openModal();
  }, [patchInfoStatus]);

  const goHome = () => {
    navigate(ROUTER_PATH.customerList);
  };

  return (
    <>
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
            <Button type="submit">
              {patchInfoStatus === 'loading' ? <LoadingSpinner /> : '저장하기'}
            </Button>
          </Wrapper>
        </ManageCafeForm>
        <PreviewContainer>
          {status === 'loading' ? (
            <>
              <SkeletonHeader />
              <SkeletonPreview />
            </>
          ) : (
            <>
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
            </>
          )}
        </PreviewContainer>
      </ManageCafeGridContainer>
      {isOpen && (
        <Alert
          text={'예기치 못한 오류가 발생하여\n카페 정보 등록에 실패했습니다.'}
          rightOption={'홈으로 돌아가기'}
          leftOption={'닫기'}
          onClickRight={goHome}
          onClickLeft={closeModal}
        />
      )}
    </>
  );
};

export default ManageCafe;
