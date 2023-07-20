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
import History from './pages/History';
import Template from './components/Template';
import CustomCouponDesign from './pages/Admin/CustomCouponDesign';
import ModifyCouponPolicy from './pages/Admin/ModifyCouponPolicy';
import EarnStamp from './pages/Admin/EarnStamp';
import SelectCoupon from './pages/Admin/SelectCoupon';
import RewardPage from './pages/Admin/RewardPage';

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
    <>
      <Header />
      <Template>
        <Outlet />
      </Template>
    </>
  );
};

const Router = () => {
  const router = createBrowserRouter([
    // 사장
    {
      path: '/admin',
      element: <AdminRoot />,
      errorElement: <NotFound />,
      children: [
        { index: true, element: <CustomerList /> },
        {
          path: 'modify-coupon-policy',
          children: [
            {
              path: '1',
              element: <ModifyCouponPolicy />,
            },
            {
              path: '2',
              element: <CustomCouponDesign />,
            },
          ],
        },
        { path: 'manage-cafe', element: <ManageCafe /> },
        { path: 'use-reward', element: <RewardPage /> },
      ],
    },
    { path: '/admin/enter-reward', element: <EnterPhoneNumber /> },
    { path: '/admin/login', element: <Login /> },
    { path: '/admin/sign-up', element: <SignUp /> },
    {
      path: 'admin/stamp',
      children: [
        { index: true, element: <EnterPhoneNumber /> },
        { path: '1', element: <SelectCoupon /> },
        { path: '2', element: <EarnStamp /> },
      ],
    },
    { path: '/admin/use-reward', element: <EnterPhoneNumber /> },
    { path: '/admin/register-cafe', element: <RegisterCafe /> },
    // 고객
    {
      path: '/',
      element: <CustomerRoot />,
      errorElement: <NotFound />,
      children: [
        { index: true, element: <CustomerList /> },
        { path: 'login', element: <Login /> },
        { path: 'sign-up', element: <SignUp /> },
        { path: 'coupon-list', element: <CouponList /> },
        { path: '/my-page', element: <MyPage /> },
        { path: '/history', element: <History /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default Router;
