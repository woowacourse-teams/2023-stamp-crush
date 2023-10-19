import useGetCouponDesign from '../../../ManageCafe/hooks/useGetCouponDesign';
import {
  CouponImageFrame,
  CurrentCouponContainer,
  ErrorContainer,
  Image,
  StampImageFrame,
} from './style';
import { useRedirectRegisterPage } from '../../../../../hooks/useRedirectRegisterPage';
import CouponLoadImg from '../../../../../assets/coupon_loading_img.png';
import StampLoadImg from '../../../../../assets/stamp_load_img.png';
import { UseQueryResult } from '@tanstack/react-query';
import { CouponDesign } from '../../../../../types/domain/coupon';
import Button from '../../../../../components/Button';

type ImgType = 'front' | 'back' | 'stamp';

const getImgURLByStatus = (query: UseQueryResult<CouponDesign, unknown>, imgType: ImgType) => {
  if (query.status === 'loading') {
    return imgType === 'stamp' ? StampLoadImg : CouponLoadImg;
  }

  if (query.status === 'error') {
    throw new Error('요청에 실패하였습니다.');
  }

  switch (imgType) {
    case 'front':
      return query.data.frontImageUrl;
    case 'back':
      return query.data.backImageUrl;
    case 'stamp':
      return query.data.stampImageUrl;
    default:
      throw new Error('잘못된 이미지 타입을 매개변수로 전달했습니다.');
  }
};

const CurrentCouponImages = () => {
  const cafeId = useRedirectRegisterPage();
  const couponDesign = useGetCouponDesign(cafeId);

  const refetchCouponDesign = () => {
    couponDesign.refetch();
  };

  if (couponDesign.status === 'error')
    return (
      <CurrentCouponContainer>
        <ErrorContainer>
          <div>오류가 발생했습니다. :(</div>
          <div>아래 새로 고침 버튼을 눌러주세요.</div>
          <div>쿠폰 미리보기를 확인하지 않으셔도</div>
          <div>쿠폰 정책을 변경하실 수 있습니다.</div>
          <Button onClick={refetchCouponDesign}>새로 고침</Button>
        </ErrorContainer>
      </CurrentCouponContainer>
    );

  return (
    <CurrentCouponContainer>
      <h1>현재 쿠폰 이미지</h1>
      <span>앞면</span>
      <CouponImageFrame>
        <Image src={getImgURLByStatus(couponDesign, 'front')} />
      </CouponImageFrame>
      <span>뒷면</span>
      <CouponImageFrame>
        <Image src={getImgURLByStatus(couponDesign, 'back')} />
      </CouponImageFrame>
      <span>스탬프</span>
      <StampImageFrame>
        <Image src={getImgURLByStatus(couponDesign, 'stamp')} />
      </StampImageFrame>
    </CurrentCouponContainer>
  );
};

export default CurrentCouponImages;
