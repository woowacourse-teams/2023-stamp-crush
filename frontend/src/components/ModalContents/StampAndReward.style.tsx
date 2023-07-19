import styled from 'styled-components';

export const Title = styled.header`
  width: 100%;
  text-align: center;

  margin-top: 20px;
  font-size: 36px;
  font-weight: 700;
`;

export const SectionTitle = styled.header`
  font-size: 24px;
  font-weight: 500;
`;

export const StampIndicator = styled.span`
  font-size: 18px;
`;

export const ContentContainer = styled.main`
  display: flex;
  flex-direction: row;
  justify-content: space-around;

  height: 90%;
`;

export const StampContainer = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  gap: 20px;

  width: 50%;
  height: 90%;
  padding: 30px 0;
`;

export const RewardContainer = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  gap: 10px;
  width: 50%;
  height: 90%;
  padding: 30px 0;

  overflow: scroll;
`;

export const RewardItemContainer = styled.ul`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
`;

export const RewardItemWrapper = styled.li`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  width: 90%;

  font-weight: 600;
  button {
    width: 80px;
    height: 32px;
    padding: 5px;
  }
`;

export const RewardContent = styled.span`
  width: 100%;
  height: 20px;

  padding: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  &:hover {
    overflow: visible;
    white-space: normal;
  }
`;

export const Divider = styled.div`
  margin: 30px 30px;
  border: 1px solid black;

  width: 1px;
  height: 80%;
`;
