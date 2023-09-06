import { styled } from 'styled-components';

export const TabBarContainer = styled.div`
  position: absolute;
  display: flex;
  justify-content: space-around;
  align-items: center;
  bottom: 0;
  width: 100%;
  max-width: 450px;
  height: 80px;
  border-radius: 8px 8px 0 0;
  box-shadow: 0px -4px 8px 0 rgba(0, 0, 0, 0.1);
  background: white;

  & > a {
    display: flex;
    width: 70px;
    flex-direction: column;
    align-items: center;
    color: #888;

    font-size: 14px;
    text-decoration: none;
    gap: 4px;
  }
`;
