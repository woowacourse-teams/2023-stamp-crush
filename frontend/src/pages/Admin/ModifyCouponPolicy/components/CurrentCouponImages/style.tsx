import styled from 'styled-components';

export const CurrentCouponContainer = styled.section`
  display: flex;
  flex-direction: column;

  border: 3px solid ${({ theme }) => theme.colors.main};
  border-radius: 16px;
  margin-left: 120px;
  padding: 16px;

  h1 {
    font-size: 20px;
    margin-bottom: 16px;
  }

  div {
    margin-bottom: 16px;
  }

  span {
    color: #777;
    margin-bottom: 8px;
  }
`;

export const CouponImageFrame = styled.div`
  width: 225px;
  height: 125px;

  background: white;
  box-shadow: 0px -2px 15px -2px #888;
`;

export const Image = styled.img`
  width: 100%;
  height: 100%;
`;

export const StampImageFrame = styled.div`
  width: 50px;
  height: 50px;
`;
