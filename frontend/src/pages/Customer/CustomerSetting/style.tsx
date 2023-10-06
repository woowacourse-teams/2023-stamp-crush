import { styled } from 'styled-components';

export const NavContainer = styled.ul`
  list-style-type: none;
`;

export const NavWrapper = styled.li`
  display: flex;
  align-items: center;
  gap: 10px;
  height: 55px;
  border-bottom: 1px solid rgba(68, 108, 125, 0.3);
  padding: 0 25px;

  cursor: pointer;
`;
