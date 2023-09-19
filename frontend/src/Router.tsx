import { RouterProvider, createBrowserRouter, Outlet } from 'react-router-dom';
import { ROUTER_PATH } from './constants';

import CustomerList from './pages/Admin/CustomerList';
import ManageCafe from './pages/Admin/ManageCafe';
import CouponList from './pages/Customer/CouponList';
import EnterPhoneNumber from './pages/Admin/EnterPhoneNumber';
import Login from './pages/Customer/Login';
import SignUp from './pages/SignUp';
import NotFound from './pages/NotFound';
import RegisterCafe from './pages/Admin/RegisterCafe';
import MyPage from './pages/Customer/MyPage';
import Template from './components/Template';
import CustomCouponDesign from './pages/Admin/CouponDesign/CustomCouponDesign';
import ModifyCouponPolicy from './pages/Admin/ModifyCouponPolicy';
import RewardPage from './pages/Admin/RewardPage';
import EarnStamp from './pages/Admin/EarnStamp';
import CustomerTemplate from './components/Template/CustomerTemplate';
import RewardList from './pages/Customer/RewardList';
import RewardHistoryPage from './pages/Customer/HistoryPage/RewardHistory';
import StampHistoryPage from './pages/Customer/HistoryPage/StampHistory';
import Auth from './pages/Auth';
import AdminLogin from './pages/Admin/AdminLogin';
import AdminAuth from './pages/Admin/AdminAuth';
import TemplateCouponDesign from './pages/Admin/CouponDesign/TemplateCouponDesign';
import InputPhoneNumber from './pages/Customer/InputPhoneNumber';
import CustomerNotFound from './pages/NotFound/CustomerNotFound';
import PrivateProvider from './provider/PrivateProvider';

const AdminRoot = () => {
  return (
    <PrivateProvider consumer={'admin'}>
      <Template>
        <Outlet />
      </Template>
    </PrivateProvider>
  );
};

const CustomerRoot = () => {
  return (
    <PrivateProvider consumer={'customer'}>
      <CustomerTemplate>
        <Outlet />
      </CustomerTemplate>
    </PrivateProvider>
  );
};

const Router = () => {
  const router = createBrowserRouter([
    // 사장
    { path: ROUTER_PATH.adminLogin, element: <AdminLogin /> },
    { path: ROUTER_PATH.adminSignup, element: <SignUp /> },
    { path: ROUTER_PATH.adminAuth, element: <AdminAuth /> },
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
          path: ROUTER_PATH.modifyCouponPolicy + ROUTER_PATH.templateCouponDesign,
          element: <TemplateCouponDesign />,
        },
        {
          path: ROUTER_PATH.modifyCouponPolicy + ROUTER_PATH.customCouponDesign,
          element: <CustomCouponDesign />,
        },
        { path: ROUTER_PATH.manageCafe, element: <ManageCafe /> },
        { path: ROUTER_PATH.registerCafe, element: <RegisterCafe /> },
        { path: ROUTER_PATH.earnStamp, element: <EarnStamp /> },
        { path: ROUTER_PATH.useReward, element: <RewardPage /> },
      ],
    },
    // 고객
    { path: ROUTER_PATH.auth, element: <Auth /> },
    { path: ROUTER_PATH.login, element: <Login /> },
    {
      path: '/',
      element: <CustomerRoot />,
      errorElement: <CustomerNotFound />,
      children: [
        { index: true, element: <CouponList /> },
        { path: ROUTER_PATH.myPage, element: <MyPage /> },
        { path: ROUTER_PATH.rewardList, element: <RewardList /> },
        { path: ROUTER_PATH.rewardHistory, element: <RewardHistoryPage /> },
        { path: ROUTER_PATH.stampHistory, element: <StampHistoryPage /> },
        { path: ROUTER_PATH.inputPhoneNumber, element: <InputPhoneNumber /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default Router;
