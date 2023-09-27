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
  border-bottom: 6px solid ${({ theme }) => theme.colors.main};
  background-color: #f7f7f7;
`;

export const Nickname = styled.span`
  font-size: 30px;
  font-weight: 700;
  margin-right: 5px;
`;

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

export const FeedbackLink = styled.a`
  text-decoration: none;
  color: ${({ theme }) => theme.colors.black};
`;
