import SideBar, { SideBarOptions } from '.';
import { PageSideBarWrapper } from './SideBar.style';

const SIDE_BAR_OPTIONS: SideBarOptions[] = [
  { key: '내 카페 관리', value: '/admin/manage-cafe' },
  { key: '내 고객 목록', value: '/admin' },
  { key: '쿠폰 정책 변경', value: '/admin/modify-coupon-policy/1' },
  { key: '스탬프 적립', value: '/admin/stamp' },
  { key: '리워드 사용', value: '/admin/use-reward' },
];

const PageSideBar = () => {
  return (
    <PageSideBarWrapper>
      <SideBar options={SIDE_BAR_OPTIONS} width={240} height={200} />
    </PageSideBarWrapper>
  );
};

export default PageSideBar;
