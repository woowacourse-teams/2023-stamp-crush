import FlippedCoupon from '../FlippedCoupon';
import Text from '../../../../components/Text';
import {
  CafeImage,
  CloseButton,
  ContentContainer,
  CouponDetailContainer,
  DeleteButton,
  DetailItem,
  OverviewContainer,
} from './style';
import { BiArrowBack } from '@react-icons/all-files/bi/BiArrowBack';
import { FaRegClock } from '@react-icons/all-files/fa/FaRegClock';
import { FaPhoneAlt } from '@react-icons/all-files/fa/FaPhoneAlt';
import { FaRegBell } from '@react-icons/all-files/fa/FaRegBell';
import { FaRegTrashAlt } from '@react-icons/all-files/fa/FaRegTrashAlt';
import { parsePhoneNumber } from '../../../../utils';
import {
  QueryObserverResult,
  RefetchOptions,
  RefetchQueryFilters,
  useMutation,
  useQuery,
} from '@tanstack/react-query';
import { getCafeInfo } from '../../../../api/get';
import { deleteCoupon } from '../../../../api/delete';
import useModal from '../../../../hooks/useModal';
import Alert from '../../../../components/Alert';
import CustomerLoadingSpinner from '../../../../components/LoadingSpinner/CustomerLoadingSpinner';
import { CouponRes } from '../../../../types/api/response';
import { Coupon } from '../../../../types/domain/coupon';
import { FaLocationDot } from '../../../../assets';

interface CouponDetailProps {
  isDetail: boolean;
  isShown: boolean;
  coupon: Coupon;
  refetchCoupons: <TPageData>(
    options?: (RefetchOptions & RefetchQueryFilters<TPageData>) | undefined,
  ) => Promise<QueryObserverResult<CouponRes, unknown>>;
  closeDetail: () => void;
}

const CouponDetail = ({
  isDetail,
  isShown,
  coupon,
  refetchCoupons,
  closeDetail,
}: CouponDetailProps) => {
  const [couponInfos] = coupon.couponInfos;
  const cafeId = coupon.cafeInfo.id;
  const { isOpen, openModal, closeModal } = useModal();

  const { data: cafeData, status: cafeStatus } = useQuery(['cafeInfos', cafeId], {
    queryFn: () => {
      return getCafeInfo({ params: { cafeId } });
    },
    enabled: cafeId !== 0,
  });

  const { mutate: mutateDeleteCoupon } = useMutation(() => deleteCoupon(couponInfos.id), {
    onSuccess: () => {
      closeDetail();
      refetchCoupons();
      closeModal();
    },
  });

  const openDeleteAlert = () => {
    openModal();
  };

  // TODO: 로딩, 에러 컴포넌트 만들기
  if (cafeStatus === 'loading') return <CustomerLoadingSpinner />;
  if (cafeStatus === 'error') return <>error</>;

  return (
    <>
      <CouponDetailContainer $isDetail={isDetail}>
        <CafeImage src={cafeData.cafe.cafeImageUrl} />
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
        <DeleteButton onClick={openDeleteAlert}>
          <FaRegTrashAlt size={24} />
        </DeleteButton>
        <OverviewContainer>
          <Text variant="subTitle">{coupon.cafeInfo.name}</Text>
          <Text>{cafeData.cafe.introduction}</Text>
        </OverviewContainer>
        <ContentContainer>
          <Text ariaLabel="쿠폰 정책">
            <FaRegBell size={22} />
            {`${couponInfos.rewardName} 무료!`}
          </Text>
          {cafeData.cafe.openTime && cafeData.cafe.closeTime && (
            <Text ariaLabel="영업 시간">
              <FaRegClock size={22} />
              {`${cafeData.cafe.openTime} - ${cafeData.cafe.closeTime}`}
            </Text>
          )}
          {cafeData.cafe.telephoneNumber && (
            <Text ariaLabel="전화번호">
              <FaPhoneAlt size={22} />
              {parsePhoneNumber(cafeData.cafe.telephoneNumber)}
            </Text>
          )}
          {cafeData.cafe.roadAddress && (
            <Text ariaLabel="주소">
              <FaLocationDot width={22} height={22} />
              {cafeData.cafe.roadAddress + ' ' + cafeData.cafe.detailAddress}
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
