import styled from 'styled-components';

export const CurrentCouponContainer = styled.section`
  display: flex;
  flex-direction: column;

  :first-child {
    font-size: 20px;
  }

  :nth-child(2n + 1) {
    margin-bottom: 16px;
  }

  :nth-child(2n + 2) {
    color: #777;
    margin-bottom: 8px;
  }
`;

export const CouponImageFrame = styled.div`
  width: 270px;
  height: 150px;

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
