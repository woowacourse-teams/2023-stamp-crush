import FlippedCoupon from '../FlippedCoupon';
import Text from '../../../components/Text';
import {
  CafeImage,
  CloseButton,
  ContentContainer,
  CouponDetailContainer,
  DeleteButton,
  OverviewContainer,
} from './style';
import { BiArrowBack } from 'react-icons/bi';
import { FaRegClock, FaPhoneAlt, FaRegBell, FaRegTrashAlt } from 'react-icons/fa';
import { FaLocationDot } from 'react-icons/fa6';
import { Coupon } from '../../../types';
import { parsePhoneNumber } from '../../../utils';
import {
  QueryObserverResult,
  RefetchOptions,
  RefetchQueryFilters,
  useMutation,
  useQuery,
} from '@tanstack/react-query';
import { CafeRes, CouponRes } from '../../../types/api';
import { getCafeInfo } from '../../../api/get';
import { deleteCoupon } from '../../../api/delete';
import useModal from '../../../hooks/useModal';
import Alert from '../../../components/Alert';

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

  const { data: cafeData, status: cafeStatus } = useQuery<CafeRes>(['cafeInfos', cafeId], {
    queryFn: () => getCafeInfo({ params: { cafeId } }),

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
  if (cafeStatus === 'loading') return null;
  if (cafeStatus === 'error') return null;

  return (
    <>
      <CouponDetailContainer $isDetail={isDetail}>
        <CafeImage src={cafeData.cafes[0].cafeImageUrl} />
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
          <Text>{cafeData.cafes[0].introduction}</Text>
        </OverviewContainer>
        <ContentContainer>
          <Text ariaLabel="쿠폰 정책">
            <FaRegBell size={22} />
            {`스탬프 ${couponInfos.maxStampCount}개를 채우면 ${couponInfos.rewardName} 무료!`}
          </Text>
          <Text ariaLabel="영업 시간">
            <FaRegClock size={22} />
            {`${cafeData.cafes[0].openTime} - ${cafeData.cafes[0].closeTime}`}
          </Text>
          <Text ariaLabel="전화번호">
            <FaPhoneAlt size={22} />
            {parsePhoneNumber(cafeData.cafes[0].telephoneNumber)}
          </Text>
          <Text ariaLabel="주소">
            <FaLocationDot size={22} />
            {cafeData.cafes[0].roadAddress + ' ' + cafeData.cafes[0].detailAddress}
          </Text>
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
