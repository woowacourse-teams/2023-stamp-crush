import CustomerList from './pages/Admin/CustomerList';
import MakeCouponPolicy from './pages/Admin/MakeCouponPolicy';
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
        { path: 'login', element: <Login /> },
        { path: 'sign-up', element: <SignUp /> },
        { path: 'register-cafe', element: <RegisterCafe /> },
        { path: 'make-coupon-policy', element: <MakeCouponPolicy /> },
        { path: 'manage-cafe', element: <ManageCafe /> },
        { path: 'enter', element: <EnterPhoneNumber /> },
      ],
    },
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
