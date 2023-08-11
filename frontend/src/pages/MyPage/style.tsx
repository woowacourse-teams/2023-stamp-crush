import { styled } from 'styled-components';

export const ArrowIconWrapper = styled.button`
  position: absolute;
  top: 20px;
  left: 20px;
  width: 24px;
  height: 24px;
  color: black;
  background: transparent;
`;

export const NicknameContainer = styled.header`
  display: flex;
  height: 150px;
  padding: 80px 0 20px 30px;
  font-size: 14px;
  align-items: flex-end;
  border-bottom: 6px solid #eee;
`;

export const Nickname = styled.span`
  font-size: 30px;
  font-weight: 700;
  margin-right: 5px;
`;

export const NavContainer = styled.li`
  list-style-type: none;
`;

export const NavWrapper = styled.ul`
  display: flex;
  align-items: center;
  height: 55px;
  border-bottom: 1px solid #eee;
  padding: 0 25px;

  cursor: pointer;
`;
