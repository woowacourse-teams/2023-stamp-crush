import { styled } from 'styled-components';

export const HeaderContainer = styled.header`
  display: flex;
  height: 59px;
  justify-content: space-between;
  align-items: center;
  padding: 0 60px;
  border-bottom: 1px solid #888;
`;

export const LogoImg = styled.img`
  height: 30px;
`;

export const LogoutButton = styled.button`
  border: none;
  background: transparent;
  color: black;
  cursor: pointer;
`;
