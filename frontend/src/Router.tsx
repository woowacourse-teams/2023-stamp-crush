import CustomerList from './pages/Admin/CustomerList';
import ManageCafe from './pages/Admin/ManageCafe';
import CouponList from './pages/CouponList';
import EnterPhoneNumber from './pages/EnterPhoneNumber';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import { RouterProvider, createBrowserRouter, Outlet } from 'react-router-dom';
import NotFound from './pages/NotFound';
import Header from './components/Header';
import RegisterCafe from './pages/Admin/RegisterCafe';
import MyPage from './pages/MyPage';
import Template from './components/Template';
import CustomCouponDesign from './pages/Admin/CustomCouponDesign';
import ModifyCouponPolicy from './pages/Admin/ModifyCouponPolicy';
import SelectCoupon from './pages/Admin/SelectCoupon';
import RewardPage from './pages/Admin/RewardPage';
import { ROUTER_PATH } from './constants';
import EarnStamp from './pages/Admin/EarnStamp';
import CustomerTemplate from './components/Template/CustomerTemplate';
import RewardList from './pages/RewardList';
import RewardHistory from './pages/RewardHistory';

const AdminRoot = () => {
  return (
    <>
      <Header />
      <Template>
        <Outlet />
      </Template>
    </>
  );
};

const CustomerRoot = () => {
  return (
    <CustomerTemplate>
      <Outlet />
    </CustomerTemplate>
  );
};

const Router = () => {
  const router = createBrowserRouter([
    // 사장
    { path: ROUTER_PATH.adminLogin, element: <Login /> },
    { path: ROUTER_PATH.adminSignup, element: <SignUp /> },
    { path: ROUTER_PATH.enterReward, element: <EnterPhoneNumber /> },
    { path: ROUTER_PATH.enterStamp, element: <EnterPhoneNumber /> },
    {
      path: '/',
      element: <AdminRoot />,
      errorElement: <NotFound />,
      children: [
        { path: ROUTER_PATH.customerList, element: <CustomerList /> },
        {
          path: ROUTER_PATH.modifyCouponPolicy,
          element: <ModifyCouponPolicy />,
        },
        {
          path: ROUTER_PATH.templateCouponDesign,
          element: <CustomCouponDesign />,
        },
        {
          path: ROUTER_PATH.customCouponDesign,
          element: <CustomCouponDesign />,
        },
        { path: ROUTER_PATH.manageCafe, element: <ManageCafe /> },
        { path: ROUTER_PATH.registerCafe, element: <RegisterCafe /> },
        { path: ROUTER_PATH.selectCoupon, element: <SelectCoupon /> },
        { path: ROUTER_PATH.earnStamp, element: <EarnStamp /> },
        { path: ROUTER_PATH.useReward, element: <RewardPage /> },
      ],
    },
    // 고객
    {
      path: '/',
      element: <CustomerRoot />,
      errorElement: <NotFound />,
      children: [
        { index: true, element: <CouponList /> },
        { path: ROUTER_PATH.login, element: <Login /> },
        { path: ROUTER_PATH.signup, element: <SignUp /> },
        { path: ROUTER_PATH.myPage, element: <MyPage /> },
        { path: ROUTER_PATH.rewardList, element: <RewardList /> },
        { path: ROUTER_PATH.rewardHistory, element: <RewardHistory /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default Router;
