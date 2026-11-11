package nhom3.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.Hoadon;
import nhom3.Utils.HibernateUtils;


public class HoadonDao {


    // =====================================================
    // Lấy tất cả hóa đơn
    // =====================================================
    public List<Hoadon> getAll(){

        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            return session.createQuery(
                    "FROM Hoadon ORDER BY maHD DESC",
                    Hoadon.class
            ).list();


        }catch(Exception e){

            System.out.println(
                "Lỗi HoadonDao.getAll: "
                + e.getMessage()
            );

            return null;
        }
    }







    // =====================================================
    // Lấy hóa đơn theo mã
    // =====================================================
    public Hoadon getById(int maHD){


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            return session.find(
                    Hoadon.class,
                    maHD
            );


        }catch(Exception e){


            System.out.println(
                "Lỗi HoadonDao.getById: "
                + e.getMessage()
            );


            return null;
        }

    }


    // =====================================================
    // Lấy hóa đơn theo khách hàng
    // =====================================================
    public List<Hoadon> getByKhachHang(int maKH){

        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){

            Query<Hoadon> query =
                    session.createQuery(

                    "FROM Hoadon " +
                    "WHERE maKH = :maKH " +
                    "ORDER BY maHD DESC",

                    Hoadon.class
                    );

            query.setParameter(
                    "maKH",
                    maKH
            );

            return query.list();

        }catch(Exception e){

            System.out.println(
                "Lỗi getByKhachHang: "
                + e.getMessage()
            );

            return null;
        }
    }
    



 // =====================================================
 // Lấy hóa đơn mới nhất của khách hàng
 // Dùng sau khi checkout
 // =====================================================
 public Hoadon getLastByKhachHang(int maKH){


     try(Session session =
             HibernateUtils.getSessionFactory()
             .openSession()){


         Query<Hoadon> query =
                 session.createQuery(

                 "FROM Hoadon " +
                 "WHERE maKH = :maKH " +
                 "ORDER BY maHD DESC",

                 Hoadon.class
                 );


         query.setParameter(
                 "maKH",
                 maKH
         );


         query.setMaxResults(1);



         return query.uniqueResult();



     }catch(Exception e){


         System.out.println(
             "Lỗi getLastByKhachHang: "
             + e.getMessage()
         );


         return null;

     }

     

 }

 public Hoadon getByThanhToan(int maThanhToan){

	    try(Session session =
	        HibernateUtils.getSessionFactory()
	        .openSession()){


	        return session.createQuery(
	        "FROM Hoadon WHERE maThanhToan=:id",
	        Hoadon.class)
	        .setParameter(
	            "id",
	            maThanhToan
	        )
	        .uniqueResult();


	    }catch(Exception e){

	        return null;
	    }

	}

//Lấy hóa đơn theo mã thanh toán
public Hoadon getByMaThanhToan(int maThanhToan){


  try(Session session =
          HibernateUtils
          .getSessionFactory()
          .openSession()){


      Query<Hoadon> query =
              session.createQuery(

              "FROM Hoadon " +
              "WHERE maThanhToan = :id",

              Hoadon.class
              );


      query.setParameter(
              "id",
              maThanhToan
      );


      return query.uniqueResult();


  }catch(Exception e){


      System.out.println(
          "Lỗi getByMaThanhToan: "
          +e.getMessage()
      );


      return null;

  }

}

    // =====================================================
    // Lấy theo trạng thái
    // =====================================================
    public List<Hoadon> getByTrangThai(String trangThai){


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            Query<Hoadon> query =
                    session.createQuery(

                    "FROM Hoadon " +
                    "WHERE trangThai = :tt",

                    Hoadon.class
                    );


            query.setParameter(
                    "tt",
                    trangThai
            );


            return query.list();



        }catch(Exception e){


            System.out.println(
                "Lỗi getByTrangThai: "
                + e.getMessage()
            );


            return null;
        }

    }









    // =====================================================
    // Lay danh sach don "CHO_XAC_NHAN" da qua han tu dong xac nhan
    // (dung cho job tu dong xac nhan don sau X phut)
    // =====================================================
    public List<Hoadon> getExpiredChoXacNhan(java.sql.Timestamp beforeTime){

        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){

            Query<Hoadon> query =
                    session.createQuery(

                    "FROM Hoadon " +
                    "WHERE trangThai = 'CHO_XAC_NHAN' " +
                    "AND ngayThanhToan <= :beforeTime",

                    Hoadon.class
                    );

            query.setParameter("beforeTime", beforeTime);

            return query.list();

        }catch(Exception e){

            System.out.println(
                "Lỗi getExpiredChoXacNhan: "
                + e.getMessage()
            );

            return null;
        }
    }


    // =====================================================
    // Lấy hóa đơn mới nhất
    // Dùng trong PaymentServlet
    // =====================================================
    public Hoadon getLastInsert(){


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            return session.createQuery(

                    "FROM Hoadon ORDER BY maHD DESC",

                    Hoadon.class

                    )
                    .setMaxResults(1)
                    .uniqueResult();



        }catch(Exception e){


            System.out.println(
                "Lỗi getLastInsert: "
                + e.getMessage()
            );


            return null;
        }

    }









    // =====================================================
    // Thêm hóa đơn
    // =====================================================
    public boolean insert(Hoadon hd){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();


            session.persist(hd);
            session.flush();

            tx.commit();


            return true;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();



            System.out.println(
                "Lỗi insert Hoadon: "
                + e.getMessage()
            );


            return false;
        }

    }









    // =====================================================
    // Cập nhật hóa đơn
    // =====================================================
    public boolean update(Hoadon hd){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();


            session.merge(hd);


            tx.commit();


            return true;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "Lỗi update Hoadon: "
                + e.getMessage()
            );


            return false;
        }

    }









    // =====================================================
    // Cập nhật trạng thái hóa đơn
    // =====================================================
    public boolean updateTrangThai(
            int maHD,
            String trangThai
    ){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();



            int rows =
                    session.createMutationQuery(

                    "UPDATE Hoadon " +
                    "SET trangThai = :tt " +
                    "WHERE maHD = :id"

                    )


                    .setParameter(
                            "tt",
                            trangThai
                    )


                    .setParameter(
                            "id",
                            maHD
                    )


                    .executeUpdate();



            tx.commit();



            return rows > 0;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "Lỗi updateTrangThai: "
                + e.getMessage()
            );


            return false;
        }

    }









    // =====================================================
    // Xóa hóa đơn
    // =====================================================
    public boolean delete(int maHD){


        Transaction tx=null;



        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();



            Hoadon hd =
                    session.find(
                        Hoadon.class,
                        maHD
                    );



            if(hd==null){


                tx.rollback();

                return false;
            }



            session.remove(hd);



            tx.commit();



            return true;



        }catch(Exception e){



            if(tx!=null)
                tx.rollback();



            System.out.println(
                "Lỗi delete Hoadon: "
                + e.getMessage()
            );


            return false;
        }

    }

}