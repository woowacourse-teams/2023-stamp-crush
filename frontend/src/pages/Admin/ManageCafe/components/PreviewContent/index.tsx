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

const PreviewContent = ({ openTime, closeTime, phoneNumber, cafeInfo }: PreviewContentProps) => {
  return (
    <PreviewContentContainer>
      <Text>
        <FaRegClock size={25} />
        {`${parseTime(openTime)} - ${parseTime(closeTime)}`}
      </Text>
      <Text>
        <FaPhoneAlt size={25} />
        {parsePhoneNumber(phoneNumber)}
      </Text>
      <Text>
        <FaLocationDot width={25} height={25} />
        {`${cafeInfo.roadAddress} ${cafeInfo.detailAddress}`}
      </Text>
    </PreviewContentContainer>
  );
};

export default PreviewContent;
