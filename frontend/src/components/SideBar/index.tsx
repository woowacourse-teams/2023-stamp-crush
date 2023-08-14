import { useLocation, useNavigate } from 'react-router-dom';
import { Logo } from '../../assets';
import { Option } from '../../types';
import {
  Container,
  EmptyContent,
  LabelContent,
  LogoHeader,
  LogoImg,
  LogoImgWrapper,
  LogoutButton,
  LogoutContainer,
  SideBarContainer,
  SideBarContent,
  SideBarLink,
} from './style';
import { useEffect, useState } from 'react';
import {
  PiUserListLight,
  PiBuildingsLight,
  PiStampLight,
  PiBookOpenTextLight,
  PiGiftLight,
} from 'react-icons/pi';
import { IoIosLogOut } from 'react-icons/io';
import { ROUTER_PATH } from '../../constants';

const SIDE_BAR_OPTIONS: Option[] = [
  { key: '', value: '' },
  { key: '내 고객 목록', value: ROUTER_PATH.customerList },
  { key: '내 카페 관리', value: ROUTER_PATH.manageCafe },
  { key: '쿠폰 제작 및 변경', value: ROUTER_PATH.modifyCouponPolicy },
  { key: '스탬프 적립', value: ROUTER_PATH.enterStamp },
  { key: '리워드 사용', value: ROUTER_PATH.enterReward },
  { key: '', value: '' },
];

const SIDEBAR_ICONS = [
  <></>,
  <PiUserListLight size={26} key="customer-list" />,
  <PiBuildingsLight size={26} key="manage-cafe" />,
  <PiBookOpenTextLight size={26} key="modify-coupon-policy" />,
  <PiStampLight size={26} key="earn-stamp" />,
  <PiGiftLight size={26} key="use-reward" />,
  <></>,
];

const SideBar = () => {
  const navigate = useNavigate();
  const options = SIDE_BAR_OPTIONS;
  const current = useLocation().pathname;
  const [currentIndex, setCurrentIndex] = useState(
    options.findIndex((option) => option.value === current) + 1,
  );
  const [isDesignCoupon, setIsDesignCoupon] = useState(false);
  const [isEarnStamp, setIsEarnStamp] = useState(false);
  const [isUseReward, setIsUseReward] = useState(false);

  const modifyPolicyCouponRoute = ROUTER_PATH.modifyCouponPolicy;
  const designCouponRoutes = [ROUTER_PATH.templateCouponDesign, ROUTER_PATH.customCouponDesign];

  const enterStamp = ROUTER_PATH.enterStamp;
  const stampRoutes = [ROUTER_PATH.selectCoupon, ROUTER_PATH.earnStamp];

  const enterReward = ROUTER_PATH.enterReward;
  const rewardRoutes = [ROUTER_PATH.useReward];

  useEffect(() => {
    const foundIndex = options.findIndex(({ value }) => {
      if (checkIncludeRoute(value, modifyPolicyCouponRoute, designCouponRoutes)) {
        setIsDesignCoupon(true);
        return true;
      }

      if (checkIncludeRoute(value, enterStamp, stampRoutes)) {
        setIsEarnStamp(true);
        return true;
      }

      if (checkIncludeRoute(value, enterReward, rewardRoutes)) {
        setIsUseReward(true);
        return true;
      }

      return value === current;
    });

    setCurrentIndex(foundIndex + 1);
  }, [current, options]);

  if (current === ROUTER_PATH.registerCafe) return <></>;

  const checkDesignCoupon = (value: string) => {
    return (
      value === modifyPolicyCouponRoute &&
      designCouponRoutes.some((route) => current.includes(route))
    );
  };
  const handleLogout = () => {
    // TODO: log out 로직
    navigate(ROUTER_PATH.adminLogin);
  };

  const checkIncludeRoute = (value: string, route: string, routes: string[]) => {
    if (value !== route) return false;
    return routes.some((route) => current.includes(route));
  };

  return (
    <>
      <Container>
        <LogoHeader $currentIndex={currentIndex}>
          <LogoImgWrapper>
            <LogoImg src={Logo} alt="스탬프크러쉬 로고" />
          </LogoImgWrapper>
        </LogoHeader>
        <SideBarContainer $prevIndex={currentIndex - 1} $nextIndex={currentIndex + 1}>
          {options.map(({ key, value }, index) => {
            if (index === 0 || index === options.length - 1) return <EmptyContent />;
            return (
              <SideBarContent
                key={key}
                $isSelected={
                  value === current ||
                  (checkIncludeRoute(value, modifyPolicyCouponRoute, designCouponRoutes) &&
                    isDesignCoupon) ||
                  (checkIncludeRoute(value, enterStamp, stampRoutes) && isEarnStamp) ||
                  (checkIncludeRoute(value, enterReward, rewardRoutes) && isUseReward)
                }
                $currentIndex={index + 1}
              >
                <SideBarLink to={value}>
                  <LabelContent
                    $isSelected={
                      value === current ||
                      (checkIncludeRoute(value, modifyPolicyCouponRoute, designCouponRoutes) &&
                        isDesignCoupon) ||
                      (checkIncludeRoute(value, enterStamp, stampRoutes) && isEarnStamp) ||
                      (checkIncludeRoute(value, enterReward, rewardRoutes) && isUseReward)
                    }
                    onClick={() => {
                      if (index === 0 || index === options.length - 1) {
                        return;
                      }
                      setCurrentIndex(index + 1);
                    }}
                  >
                    {SIDEBAR_ICONS[index]}
                    {key}
                  </LabelContent>
                </SideBarLink>
              </SideBarContent>
            );
          })}
        </SideBarContainer>
        <LogoutContainer $currentIndex={currentIndex}>
          <LogoutButton onClick={handleLogout}>
            <IoIosLogOut size="26px" />
            로그아웃
          </LogoutButton>
        </LogoutContainer>
      </Container>
      {/* <CharacterImage src={Character} /> */}
    </>
  );
};

export default SideBar;
