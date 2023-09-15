import Color from 'color-thief-react';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
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

interface CafeInfoProps {
  cafeName: string;
  stampCount: number;
  maxStampCount: number;
  isFavorites: boolean;
  frontImageUrl: string;
  onClickStar: () => void;
}

const CafeInfo = ({
  cafeName,
  stampCount,
  maxStampCount,
  isFavorites,
  frontImageUrl,
  onClickStar,
}: CafeInfoProps) => {
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
                <ProgressBar stampCount={stampCount} maxCount={maxStampCount} color={color} />
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
