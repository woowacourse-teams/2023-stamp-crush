import { RouterProvider, createBrowserRouter, Outlet } from 'react-router-dom';
import CustomerList from './pages/Admin/CustomerList';
import ManageCafe from './pages/Admin/ManageCafe';
import CouponList from './pages/Customer/CouponList';
import EnterPhoneNumber from './pages/Admin/EnterPhoneNumber';
import Login from './pages/Customer/Login';
import SignUp from './pages/Admin/SignUp';
import NotFound from './pages/NotFound';
import RegisterCafe from './pages/Admin/RegisterCafe';
import MyPage from './pages/Customer/MyPage';
import Template from './components/Template';
import ModifyCouponPolicy from './pages/Admin/ModifyCouponPolicy';
import RewardPage from './pages/Admin/RewardPage';
import EarnStamp from './pages/Admin/EarnStamp';
import CustomerTemplate from './components/Template/CustomerTemplate';
import RewardList from './pages/Customer/RewardList';
import Auth from './pages/Customer/Auth';
import AdminLogin from './pages/Admin/AdminLogin';
import AdminAuth from './pages/Admin/AdminAuth';
import InputPhoneNumber from './pages/Customer/InputPhoneNumber';
import CustomerNotFound from './pages/NotFound/CustomerNotFound';
import PrivateProvider from './provider/PrivateProvider';
import CustomerCancellation from './pages/Customer/Cancellation';
import CustomerSetting from './pages/Customer/CustomerSetting';
import Greeting from './pages/Customer/Greeting';
import CustomerProfileProvider from './provider/CustomerProfileProvider';
import ROUTER_PATH from './constants/routerPath';
import RewardHistoryPage from './pages/Customer/HistoryPage/components/RewardHistory';
import StampHistoryPage from './pages/Customer/HistoryPage/components/StampHistory';
import TemplateCouponDesign from './pages/Admin/TemplateCouponDesign';
import CustomCouponDesign from './pages/Admin/CustomCouponDesign';
import Introduction from './pages/Introduction';
import { AdminAccessTokenProvider } from './context/accessToken';

const AdminRoot = () => {
  return (
    <AdminAccessTokenProvider>
      <PrivateProvider consumer={'admin'}>
        <Template>
          <Outlet />
        </Template>
      </PrivateProvider>
    </AdminAccessTokenProvider>
  );
};

const CustomerRoot = () => {
  return (
    <PrivateProvider consumer={'customer'}>
      <CustomerProfileProvider>
        <CustomerTemplate>
          <Outlet />
        </CustomerTemplate>
      </CustomerProfileProvider>
    </PrivateProvider>
  );
};

const Router = () => {
  const router = createBrowserRouter([
    // 소개 페이지
    { path: ROUTER_PATH.introduction, element: <Introduction /> },
    // 사장
    { path: ROUTER_PATH.adminLogin, element: <AdminLogin /> },
    { path: ROUTER_PATH.adminSignUp, element: <SignUp /> },
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
    { path: ROUTER_PATH.greeting, element: <Greeting /> },
    {
      path: '/',
      element: <CustomerRoot />,
      errorElement: <CustomerNotFound />,
      children: [
        { path: ROUTER_PATH.couponList, element: <CouponList /> },
        { path: ROUTER_PATH.myPage, element: <MyPage /> },
        { path: ROUTER_PATH.rewardList, element: <RewardList /> },
        { path: ROUTER_PATH.rewardHistory, element: <RewardHistoryPage /> },
        { path: ROUTER_PATH.stampHistory, element: <StampHistoryPage /> },
        { path: ROUTER_PATH.inputPhoneNumber, element: <InputPhoneNumber /> },
        { path: ROUTER_PATH.customerCancellation, element: <CustomerCancellation /> },
        { path: ROUTER_PATH.customerSetting, element: <CustomerSetting /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default Router;
