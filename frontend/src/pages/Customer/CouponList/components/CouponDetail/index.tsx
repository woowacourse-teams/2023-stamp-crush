import FlippedCoupon from '../FlippedCoupon';
import Text from '../../../../../components/Text';
import {
  CafeImage,
  CloseButton,
  ContentContainer,
  CouponDetailContainer,
  DeleteButton,
  OverviewContainer,
} from './style';
import { BiArrowBack } from '@react-icons/all-files/bi/BiArrowBack';
import { FaRegClock } from '@react-icons/all-files/fa/FaRegClock';
import { FaPhoneAlt } from '@react-icons/all-files/fa/FaPhoneAlt';
import { FaRegBell } from '@react-icons/all-files/fa/FaRegBell';
import { FaRegTrashAlt } from '@react-icons/all-files/fa/FaRegTrashAlt';
import { parsePhoneNumber } from '../../../../../utils';
import useModal from '../../../../../hooks/useModal';
import Alert from '../../../../../components/Alert';
import CustomerLoadingSpinner from '../../../../../components/LoadingSpinner/CustomerLoadingSpinner';
import { Coupon } from '../../../../../types/domain/coupon';
import { FaLocationDot } from '../../../../../assets';
import { DefaultCafeImage } from '../../../../Admin/ManageCafe/style';
import useGetCafeInfo from '../../hooks/useGetCafeInfo';
import useDeleteCoupon from '../../hooks/useDeleteCoupon';

interface CouponDetailProps {
  isDetail: boolean;
  isShown: boolean;
  coupon: Coupon;
  closeDetail: () => void;
}

const CouponDetail = ({ isDetail, isShown, coupon, closeDetail }: CouponDetailProps) => {
  const { isOpen, openModal, closeModal } = useModal();
  const [couponInfos] = coupon.couponInfos;
  const cafeId = coupon.cafeInfo.id;
  const { data: cafeInfo, status: cafeStatus } = useGetCafeInfo(cafeId);
  const { mutate: mutateDeleteCoupon } = useDeleteCoupon(couponInfos.id, closeDetail, closeModal);

  // TODO: 로딩, 에러 컴포넌트 만들기
  if (cafeStatus === 'loading') return <CustomerLoadingSpinner />;
  if (cafeStatus === 'error') return <>error</>;

  return (
    <>
      <CouponDetailContainer $isDetail={isDetail}>
        {!cafeInfo.cafeImageUrl ? <DefaultCafeImage /> : <CafeImage src={cafeInfo.cafeImageUrl} />}
        <FlippedCoupon
          frontImageUrl={couponInfos.frontImageUrl}
          backImageUrl={couponInfos.backImageUrl}
          stampImageUrl={couponInfos.stampImageUrl}
          isShown={isShown}
          coordinates={couponInfos.coordinates}
          stampCount={couponInfos.stampCount}
        />
        <CloseButton onClick={closeDetail} aria-label="홈으로 돌아가기" role="button">
          <BiArrowBack size={24} />
        </CloseButton>
        <DeleteButton onClick={openModal}>
          <FaRegTrashAlt size={24} />
        </DeleteButton>
        <OverviewContainer>
          <Text variant="subTitle">{coupon.cafeInfo.name}</Text>
          <Text>{cafeInfo.introduction}</Text>
        </OverviewContainer>
        <ContentContainer>
          <Text ariaLabel="쿠폰 정책">
            <FaRegBell size={22} />
            {`${couponInfos.rewardName} 무료!`}
          </Text>
          {cafeInfo.openTime && cafeInfo.closeTime && (
            <Text ariaLabel="영업 시간">
              <FaRegClock size={22} />
              {`${cafeInfo.openTime} - ${cafeInfo.closeTime}`}
            </Text>
          )}
          {cafeInfo.telephoneNumber && (
            <Text ariaLabel="전화번호">
              <FaPhoneAlt size={22} />
              {parsePhoneNumber(cafeInfo.telephoneNumber)}
            </Text>
          )}
          {cafeInfo.roadAddress && (
            <Text ariaLabel="주소">
              <FaLocationDot width={22} height={22} />
              {cafeInfo.roadAddress + ' ' + cafeInfo.detailAddress}
            </Text>
          )}
        </ContentContainer>
      </CouponDetailContainer>
      {isOpen && (
        <Alert
          text="쿠폰을 삭제하시겠습니까?"
          rightOption={'네'}
          leftOption={'아니오'}
          onClickRight={mutateDeleteCoupon}
          onClickLeft={closeModal}
        />
      )}
    </>
  );
};

export default CouponDetail;
