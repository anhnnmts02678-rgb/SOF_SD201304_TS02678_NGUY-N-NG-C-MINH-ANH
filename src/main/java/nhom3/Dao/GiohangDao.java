package nhom3.Dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.Giohang;
import nhom3.Utils.HibernateUtils;



public class GiohangDao {


    // =================================================
    // Lấy tất cả giỏ hàng
    // Admin dùng
    // =================================================

    public List<Giohang> getAll(){

        try(Session session =
            HibernateUtils.getSessionFactory()
            .openSession()){


            return session.createQuery(
                "FROM Giohang ORDER BY maGH DESC",
                Giohang.class
            ).list();


        }catch(Exception e){

            System.out.println(
                "GiohangDao.getAll: "
                + e.getMessage()
            );

            return null;
        }

    }



    // =================================================
    // Lấy giỏ theo mã
    // =================================================

    public Giohang getById(int maGH){


        try(Session session =
            HibernateUtils.getSessionFactory()
            .openSession()){


            return session.find(
                Giohang.class,
                maGH
            );


        }catch(Exception e){

            System.out.println(
                "GiohangDao.getById: "
                + e.getMessage()
            );

            return null;
        }

    }




    // =================================================
    // Lấy giỏ đang mua của khách
    //
    // 1 khách chỉ có 1 giỏ active
    //
    // =================================================

    public Giohang getActiveByKhachHang(int maKH) {

        try(Session session = HibernateUtils.getSessionFactory().openSession()){

            return session.createQuery(
                    "FROM Giohang WHERE maKH=:maKH AND trangThai=true ORDER BY maGH DESC",
                    Giohang.class)
                    .setParameter("maKH", maKH)
                    .setMaxResults(1)
                    .uniqueResult();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    // =================================================
    // Lấy lịch sử giỏ của khách
    // =================================================

    public List<Giohang> getByKhachHang(int maKH){


        try(Session session =
            HibernateUtils.getSessionFactory()
            .openSession()){


            Query<Giohang> query =
                session.createQuery(

                "FROM Giohang " +
                "WHERE maKH=:maKH " +
                "ORDER BY maGH DESC",

                Giohang.class
            );


            query.setParameter(
                "maKH",
                maKH
            );


            return query.list();



        }catch(Exception e){


            System.out.println(
                "GiohangDao.getByKhachHang: "
                + e.getMessage()
            );


            return null;

        }

    }







    // =================================================
    // Tạo giỏ hàng
    // =================================================

    public boolean insert(Giohang gh){

        Transaction tx = null;

        try(Session session =
                HibernateUtils.getSessionFactory()
                .openSession()){

            tx = session.beginTransaction();

            session.persist(gh);

            tx.commit();

            return true;

        }catch(Exception e){

            System.out.println("===== LOI TAO GIO HANG =====");
            e.printStackTrace();

            try{
                if(tx != null && tx.isActive()){
                    tx.rollback();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            return false;
        }
    }







    // =================================================
    // Update giỏ hàng
    // =================================================

    public boolean update(Giohang gh){


        Transaction tx=null;


        try(Session session =
            HibernateUtils.getSessionFactory()
            .openSession()){


            tx=session.beginTransaction();


            session.merge(gh);


            tx.commit();


            return true;


        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            return false;
        }


    }






    // =================================================
    // Đóng giỏ sau khi đặt hàng
    //
    // PaymentServlet gọi hàm này
    //
    // =================================================


    public boolean dongGioHang(int maGH){


        Transaction tx=null;


        try(Session session =
            HibernateUtils.getSessionFactory()
            .openSession()){


            tx=session.beginTransaction();



            int rows =
            session.createMutationQuery(

                "UPDATE Giohang " +
                "SET trangThai=false " +
                "WHERE maGH=:id"

            )


            .setParameter(
                "id",
                maGH
            )


            .executeUpdate();



            tx.commit();



            return rows>0;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "GiohangDao.dongGioHang: "
                +e.getMessage()
            );


            return false;
        }

    }

    public boolean dongGioHangTheoKH(int maKH){

        Transaction tx = null;

        try(Session session =
                HibernateUtils.getSessionFactory()
                .openSession()){

            tx = session.beginTransaction();

            int rows =
                session.createMutationQuery(

                "UPDATE Giohang " +
                "SET trangThai=false " +
                "WHERE maKH=:id " +
                "AND trangThai=true"

                )

                .setParameter(
                        "id",
                        maKH
                )

                .executeUpdate();

            tx.commit();

            return rows > 0;

        }catch(Exception e){

            if(tx != null)
                tx.rollback();

            System.out.println(
                "GiohangDao.dongGioHangTheoKH: "
                + e.getMessage()
            );

            return false;
        }
    }




    // =================================================
    // Xóa giỏ
    // Chỉ dùng test
    // =================================================

    public boolean delete(int maGH){


        Transaction tx=null;


        try(Session session =
            HibernateUtils.getSessionFactory()
            .openSession()){


            tx=session.beginTransaction();



            Giohang gh =
            session.find(
                Giohang.class,
                maGH
            );



            if(gh==null){

                tx.rollback();

                return false;
            }



            session.remove(gh);



            tx.commit();



            return true;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            return false;

        }

    }


}