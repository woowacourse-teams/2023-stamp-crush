import { useLocation } from 'react-router-dom';
import { StampcrushLogo } from '../../assets';
import { Option } from '../../types';
import {
  LabelContent,
  LogoHeader,
  LogoImg,
  LogoImgWrapper,
  SideBarContainer,
  SideBarContent,
  SideBarLink,
} from './style';
import { useEffect, useState } from 'react';
import { ROUTER_PATH } from '../../constants';

interface SideBarProps {
  width: number;
  height: number;
  options: Option[];
}

const SideBar = ({ width, height, options }: SideBarProps) => {
  const current = useLocation().pathname;
  const [currentIndex, setCurrentIndex] = useState(
    options.findIndex((option) => option.value === current) + 1,
  );
  const [isDesignCoupon, setIsDesignCoupon] = useState(false);

  const modifyPolicyCouponRoute = ROUTER_PATH.modifyCouponPolicy;
  const designCouponRoutes = [ROUTER_PATH.templateCouponDesign, ROUTER_PATH.customCouponDesign];

  useEffect(() => {
    setIsDesignCoupon(false);

    const foundIndex = options.findIndex(({ value }) => {
      if (checkDesignCoupon(value)) {
        setIsDesignCoupon(true);
        return true;
      }
      return value === current;
    });

    setCurrentIndex(foundIndex + 1);
  }, [current, options]);

  const checkDesignCoupon = (value: string) => {
    return (
      value === modifyPolicyCouponRoute &&
      designCouponRoutes.some((route) => current.includes(route))
    );
  };

  return (
    <>
      <LogoHeader>
        <LogoImgWrapper>
          <LogoImg src={StampcrushLogo} alt="스탬프크러쉬 로고" />
        </LogoImgWrapper>
      </LogoHeader>
      <SideBarContainer
        $width={width}
        $height={height}
        $prevIndex={currentIndex - 1}
        $nextIndex={currentIndex + 1}
      >
        {options.map(({ key, value }, index) => (
          <SideBarContent
            key={key}
            $isSelected={value === current || (checkDesignCoupon(value) && isDesignCoupon)}
            $currentIndex={index + 1}
          >
            <SideBarLink to={value}>
              <LabelContent
                $isSelected={value === current || (checkDesignCoupon(value) && isDesignCoupon)}
                $width={width}
                $height={height / options.length}
                onClick={() => setCurrentIndex(index + 1)}
              >
                {key}
              </LabelContent>
            </SideBarLink>
          </SideBarContent>
        ))}
      </SideBarContainer>
    </>
  );
};

export default SideBar;
