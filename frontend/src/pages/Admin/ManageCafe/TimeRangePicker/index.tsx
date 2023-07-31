import React, { Dispatch, SetStateAction } from 'react';
import { TimePickerWrapper, TimeRangePickerContainer } from './style';
import { Time } from '..';

interface TimePickerProps {
  startTime: Time;
  setStartTime: Dispatch<SetStateAction<Time>>;
  endTime: Time;
  setEndTime: Dispatch<SetStateAction<Time>>;
}

const TimePicker = ({ startTime, endTime, setEndTime, setStartTime }: TimePickerProps) => {
  const handleStartTimeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = event.target;
    setStartTime((prevStartTime) => ({
      ...prevStartTime,
      [name]: value,
    }));
  };

  const handleEndTimeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = event.target;
    setEndTime((prevEndTime) => ({
      ...prevEndTime,
      [name]: value,
    }));
  };

  const hours = Array.from({ length: 24 }, (_, index) => index.toString().padStart(2, '0'));
  const minutes = Array.from({ length: 6 }, (_, index) => (index * 10).toString().padStart(2, '0'));

  return (
    <TimeRangePickerContainer>
      <TimePickerWrapper>
        <select name="hour" value={startTime.hour} onChange={handleStartTimeChange}>
          {hours.map((hour) => (
            <option key={hour} value={hour}>
              {hour}
            </option>
          ))}
        </select>
        :
        <select name="minute" value={startTime.minute} onChange={handleStartTimeChange}>
          {minutes.map((minute) => (
            <option key={minute} value={minute}>
              {minute}
            </option>
          ))}
        </select>
      </TimePickerWrapper>
      ~
      <TimePickerWrapper>
        <select name="hour" value={endTime.hour} onChange={handleEndTimeChange}>
          {hours.map((hour) => (
            <option key={hour} value={hour}>
              {hour}
            </option>
          ))}
        </select>
        :
        <select name="minute" value={endTime.minute} onChange={handleEndTimeChange}>
          {minutes.map((minute) => (
            <option key={minute} value={minute}>
              {minute}
            </option>
          ))}
        </select>
      </TimePickerWrapper>
    </TimeRangePickerContainer>
  );
};

export default TimePicker;
