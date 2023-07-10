import { Link } from 'react-router-dom';
import { styled } from 'styled-components';

export const GlobalHeader = styled.header`
  display: flex;
  height: 59px;
  justify-content: space-between;
  align-items: center;
  padding: 0 60px;
  border-bottom: 1px solid #888;
`;

export const LogoImg = styled.img`
  height: 38px;
`;

export const LoginLink = styled(Link)`
  color: black;
  text-decoration: none;
`;
