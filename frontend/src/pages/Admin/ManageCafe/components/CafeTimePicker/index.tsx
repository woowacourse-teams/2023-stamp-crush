import { Time } from '../../../../../types/utils';
import { StepTitle, Wrapper } from '../../style';
import TimePicker from '../../TimeRangePicker';

interface CafeTimerPicker {
  openTime: Time;
  closeTime: Time;
  setOpenTime: React.Dispatch<React.SetStateAction<Time>>;
  setCloseTime: React.Dispatch<React.SetStateAction<Time>>;
}

const CafeTimePicker = ({ openTime, closeTime, setOpenTime, setCloseTime }: CafeTimerPicker) => {
  return (
    <Wrapper>
      <StepTitle>step3. 내 카페의 영업 시간을 알려주세요.</StepTitle>
      <TimePicker
        startTime={openTime}
        endTime={closeTime}
        setStartTime={setOpenTime}
        setEndTime={setCloseTime}
      />
    </Wrapper>
  );
};

export default CafeTimePicker;
