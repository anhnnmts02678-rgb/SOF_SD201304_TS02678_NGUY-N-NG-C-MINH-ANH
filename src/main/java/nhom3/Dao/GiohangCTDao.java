package nhom3.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.GiohangCT;
import nhom3.Utils.HibernateUtils;


public class GiohangCTDao {


    // =====================================================
    // Lấy danh sách sản phẩm trong giỏ hàng
    // =====================================================
    public List<GiohangCT> getByGioHang(int maGH){

        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            Query<GiohangCT> query =
                    session.createQuery(
                    "FROM GiohangCT WHERE maGH = :maGH",
                    GiohangCT.class
                    );


            query.setParameter(
                    "maGH",
                    maGH
            );


            return query.list();


        }catch(Exception e){

            System.out.println(
                "Lỗi getByGioHang: "
                + e.getMessage()
            );

            return null;
        }
    }




    // =====================================================
    // Lấy chi tiết giỏ hàng theo mã
    // =====================================================
    public GiohangCT getById(int maGHCT){

        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            return session.find(
                    GiohangCT.class,
                    maGHCT
            );


        }catch(Exception e){

            System.out.println(
                "Lỗi getById: "
                + e.getMessage()
            );

            return null;
        }
    }


    public boolean remove(int maGHCT){


    	Transaction tx=null;


    	try(Session session=
    	HibernateUtils
    	.getSessionFactory()
    	.openSession()){


    	tx=session.beginTransaction();


    	GiohangCT ct=
    	session.find(
    	GiohangCT.class,
    	maGHCT);



    	if(ct!=null){

    	session.remove(ct);

    	}


    	tx.commit();


    	return true;



    	}catch(Exception e){


    	if(tx!=null)
    	tx.rollback();


    	return false;

    	}


    	}


    // =====================================================
    // Kiểm tra sản phẩm đã có trong giỏ chưa
    // =====================================================
    public GiohangCT getByGioHangVaSanPham(
            int maGH,
            int maSP
    ){

        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            Query<GiohangCT> query =
                    session.createQuery(
                    "FROM GiohangCT " +
                    "WHERE maGH = :maGH " +
                    "AND maSP = :maSP",
                    GiohangCT.class
                    );


            query.setParameter(
                    "maGH",
                    maGH
            );


            query.setParameter(
                    "maSP",
                    maSP
            );


            return query.uniqueResult();


        }catch(Exception e){

            System.out.println(
                "Lỗi getByGioHangVaSanPham: "
                + e.getMessage()
            );

            return null;
        }
    }





    // =====================================================
    // Thêm sản phẩm vào giỏ
    // =====================================================
    public boolean insert(GiohangCT ghct){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();


            session.persist(ghct);


            tx.commit();


            return true;


        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "Lỗi insert GiohangCT: "
                + e.getMessage()
            );


            return false;
        }
    }







    // =====================================================
    // Cập nhật số lượng
    // =====================================================
    public boolean updateSoLuong(
            int maGHCT,
            int soLuong,
            double tongGia
    ){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();



            int rows =
                    session.createMutationQuery(

                    "UPDATE GiohangCT " +
                    "SET soLuong = :sl, " +
                    "tongGia = :gia " +
                    "WHERE maGHCT = :id"

                    )


                    .setParameter(
                        "sl",
                        soLuong
                    )


                    .setParameter(
                        "gia",
                        tongGia
                    )


                    .setParameter(
                        "id",
                        maGHCT
                    )


                    .executeUpdate();



            tx.commit();



            return rows > 0;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "Lỗi updateSoLuong: "
                + e.getMessage()
            );


            return false;
        }
    }







    // =====================================================
    // Update toàn bộ
    // =====================================================
    public boolean update(GiohangCT ghct){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();


            session.merge(ghct);


            tx.commit();


            return true;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "Lỗi update: "
                + e.getMessage()
            );


            return false;
        }
    }









    // =====================================================
    // Xóa 1 sản phẩm khỏi giỏ
    // =====================================================
    public boolean delete(int maGHCT){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();



            GiohangCT ghct =
                    session.find(
                        GiohangCT.class,
                        maGHCT
                    );



            if(ghct==null){

                tx.rollback();

                return false;
            }



            session.remove(ghct);



            tx.commit();


            return true;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "Lỗi delete: "
                + e.getMessage()
            );


            return false;
        }
    }









    // =====================================================
    // Xóa toàn bộ sản phẩm trong giỏ
    // =====================================================
    public boolean deleteByGioHang(int maGH){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();



            int rows =
                    session.createMutationQuery(

                    "DELETE FROM GiohangCT " +
                    "WHERE maGH = :maGH"

                    )


                    .setParameter(
                        "maGH",
                        maGH
                    )


                    .executeUpdate();



            tx.commit();



            return rows > 0;



        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            System.out.println(
                "Lỗi deleteByGioHang: "
                + e.getMessage()
            );


            return false;
        }
    }








    // =====================================================
    // Tính tổng tiền giỏ hàng
    // =====================================================
    public double getTongTien(int maGH){


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            Double tong =
                    session.createQuery(

                    "SELECT SUM(tongGia) " +
                    "FROM GiohangCT " +
                    "WHERE maGH = :maGH",

                    Double.class

                    )


                    .setParameter(
                        "maGH",
                        maGH
                    )


                    .uniqueResult();



            return tong == null ? 0 : tong;



        }catch(Exception e){


            System.out.println(
                "Lỗi getTongTien: "
                + e.getMessage()
            );


            return 0;
        }
    }

}