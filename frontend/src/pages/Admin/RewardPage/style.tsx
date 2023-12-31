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
  justify-content: center;
  gap: 10px;
  width: 70%;
`;

export const Divider = styled.div`
  margin: 30px 30px;
  border: 1px solid black;

  width: 1px;
  height: 80%;
`;
