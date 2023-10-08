import Text from '../../../../../components/Text';
import { FaRegClock } from '@react-icons/all-files/fa/FaRegClock';
import { FaPhoneAlt } from '@react-icons/all-files/fa/FaPhoneAlt';
import { parsePhoneNumber, parseTime } from '../../../../../utils';
import { Cafe } from '../../../../../types/domain/cafe';
import { Time } from '../../../../../types/utils';
import { PreviewContentContainer } from './style';
import { FaLocationDot } from '../../../../../assets';

interface PreviewContentProps {
  openTime: Time;
  closeTime: Time;
  phoneNumber: string;
  cafeInfo: Cafe;
}

//TODO: cafes API에 리워드명 받을지 논의
const PreviewContent = ({ openTime, closeTime, phoneNumber, cafeInfo }: PreviewContentProps) => {
  return (
    <PreviewContentContainer>
      <Text>
        <FaRegClock size={22} />
        {`${parseTime(openTime)} - ${parseTime(closeTime)}`}
      </Text>
      <Text>
        <FaPhoneAlt size={22} />
        {parsePhoneNumber(phoneNumber)}
      </Text>
      <Text>
        <FaLocationDot width={22} height={22} />
        {`${cafeInfo.roadAddress} ${cafeInfo.detailAddress}`}
      </Text>
    </PreviewContentContainer>
  );
};

export default PreviewContent;
