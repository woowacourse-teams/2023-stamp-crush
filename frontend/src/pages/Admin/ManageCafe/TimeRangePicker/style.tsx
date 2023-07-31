import styled from 'styled-components';

export const TimeRangePickerContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  gap: 30px;

  select {
    width: 50px;
    height: 30px;
    font-size: 1rem;
    border: none;
    border-radius: 8px;
    background-color: transparent;
    text-align: center;
    outline: none;

    transition: all 0.3s;
  }

  select:hover {
    color: tomato;
    font-weight: 500;
  }
`;

export const TimePickerWrapper = styled.div`
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 2px;
`;
