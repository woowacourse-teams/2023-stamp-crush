import Color from 'color-thief-react';
import { AiFillStar } from '@react-icons/all-files/ai/AiFillStar';
import { AiOutlineStar } from '@react-icons/all-files/ai/AiOutlineStar';
import ProgressBar from '../../../../../components/ProgressBar';
import { addGoogleProxyUrl } from '../../../../../utils';
import {
  BackDrop,
  CafeName,
  InfoContainer,
  MaxStampCount,
  NameContainer,
  ProgressBarContainer,
  StampCount,
} from './style';
import { Coupon, CouponInfo } from '../../../../../types/domain/coupon';

interface CafeInfoProps {
  cafeInfo: Coupon;
  couponInfo: CouponInfo;
  onClickStar: () => void;
}

const CafeInfo = ({ cafeInfo: { cafeInfo }, couponInfo, onClickStar }: CafeInfoProps) => {
  const { name: cafeName, isFavorites } = cafeInfo;
  const { stampCount, maxStampCount, frontImageUrl } = couponInfo;
  return (
    <>
      <InfoContainer>
        <NameContainer>
          <CafeName aria-label="카페 이름">{cafeName}</CafeName>
          {isFavorites ? (
            <AiFillStar
              size={40}
              color={'#FFD600'}
              onClick={onClickStar}
              aria-label="즐겨찾기 해제"
              role="button"
            />
          ) : (
            <AiOutlineStar
              size={40}
              color={'#FFD600'}
              onClick={onClickStar}
              aria-label="즐겨찾기 등록"
              role="button"
            />
          )}
        </NameContainer>
        <ProgressBarContainer aria-label="스탬프 개수">
          <Color src={addGoogleProxyUrl(frontImageUrl)} format="hex" crossOrigin="anonymous">
            {({ data: color }) => (
              <>
                <BackDrop $couponMainColor={color ? color : 'gray'} />
                <ProgressBar stampCount={stampCount} maxStampCount={maxStampCount} color={color} />
              </>
            )}
          </Color>
          <StampCount aria-label={`현재 스탬프 개수 ${stampCount}개`}>{stampCount}</StampCount>/
          <MaxStampCount aria-label="필요한 스탬프 개수">{maxStampCount}</MaxStampCount>
        </ProgressBarContainer>
      </InfoContainer>
    </>
  );
};

export default CafeInfo;
