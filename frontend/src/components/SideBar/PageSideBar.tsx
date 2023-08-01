import SideBar from '.';
import { ROUTER_PATH } from '../../constants';
import { Option } from '../../types';
import { PageSideBarWrapper } from './style';

const SIDE_BAR_OPTIONS: Option[] = [
  { key: '내 카페 관리', value: ROUTER_PATH.manageCafe },
  { key: '내 고객 목록', value: ROUTER_PATH.customerList },
  { key: '쿠폰 정책 변경', value: ROUTER_PATH.modifyCouponPolicy },
  { key: '스탬프 적립', value: ROUTER_PATH.enterStamp },
  { key: '리워드 사용', value: ROUTER_PATH.enterReward },
];

const PageSideBar = () => {
  return (
    <PageSideBarWrapper>
      <SideBar options={SIDE_BAR_OPTIONS} width={240} height={200} />
    </PageSideBarWrapper>
  );
};

export default PageSideBar;
