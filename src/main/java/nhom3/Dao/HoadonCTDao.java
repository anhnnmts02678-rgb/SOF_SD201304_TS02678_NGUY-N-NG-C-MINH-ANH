package nhom3.Dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import nhom3.Model.HoadonCT;
import nhom3.Utils.HibernateUtils;

public class HoadonCTDao {

    /**
     * Use case: Danh gia - xac dinh cac san pham ma 1 khach hang
     * DA MUA VA DA NHAN HANG (Hoadon.trangThai = 'DA_GIAO').
     * Dung de mo khoa quyen viet danh gia o products.jsp / product-detail.jsp
     * va de validate lai o server (DanhgiaServlet) truoc khi luu.
     */
    public Set<Integer> getPurchasedSanPhamIds(int maKH) {
        Set<Integer> result = new HashSet<>();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Integer> q = session.createQuery(
                    "SELECT ct.maSP FROM HoadonCT ct, Hoadon hd " +
                    "WHERE ct.maHD = hd.maHD AND hd.maKH = :maKH AND hd.trangThai = 'DA_GIAO'",
                    Integer.class);
            q.setParameter("maKH", maKH);
            result.addAll(q.list());
            return result;
        } catch (Exception e) {
            System.out.println("Loi HoadonCTDao.getPurchasedSanPhamIds: " + e.getMessage());
            return result;
        }
    }

    /**
     * Use case: Danh gia - kiem tra 1 khach hang co da mua VA da nhan
     * 1 san pham cu the hay chua (dung de validate ngay ca khi bi bypass
     * form phia client).
     */
    public boolean daMuaVaDaNhan(int maKH, int maSP) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Long> q = session.createQuery(
                    "SELECT COUNT(ct) FROM HoadonCT ct, Hoadon hd " +
                    "WHERE ct.maHD = hd.maHD AND hd.maKH = :maKH " +
                    "AND ct.maSP = :maSP AND hd.trangThai = 'DA_GIAO'",
                    Long.class);
            q.setParameter("maKH", maKH);
            q.setParameter("maSP", maSP);
            Long count = q.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            System.out.println("Loi HoadonCTDao.daMuaVaDaNhan: " + e.getMessage());
            return false;
        }
    }


    // Lấy danh sách sản phẩm trong hóa đơn
	public List<HoadonCT> getByHoaDon(int maHD){


	    try(Session session =
	        HibernateUtils
	        .getSessionFactory()
	        .openSession()){


	        Query<HoadonCT> query =
	        session.createQuery(

	        "FROM HoadonCT " +
	        "WHERE maHD=:id",

	        HoadonCT.class
	        );


	        query.setParameter(
	                "id",
	                maHD
	        );


	        return query.list();


	    }catch(Exception e){


	        return null;

	    }

	}

	public List<HoadonCT> getByMaHD(int maHD){

	    try(Session session =
	            HibernateUtils.getSessionFactory().openSession()){

	        return session.createQuery(
	                "FROM HoadonCT WHERE maHD = :maHD",
	                HoadonCT.class
	        )
	        .setParameter("maHD", maHD)
	        .list();

	    }catch(Exception e){

	        e.printStackTrace();
	        return null;
	    }
	}



    // Lấy chi tiết theo mã
    public HoadonCT getById(int maHDCT){


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            return session.find(
                    HoadonCT.class,
                    maHDCT
            );


        }catch(Exception e){

            return null;
        }

    }






    // Thêm 1 chi tiết
    public boolean insert(HoadonCT hdct){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();


            session.persist(hdct);

            session.flush();

            tx.commit();


            return true;


        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            return false;

        }
    }





    // Thêm nhiều sản phẩm cùng lúc
    public boolean insertBatch(List<HoadonCT> list){

        Transaction tx = null;

        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx = session.beginTransaction();


            for(int i = 0; i < list.size(); i++){


                session.persist(list.get(i));


                // mỗi 20 dòng thì ghi xuống DB
                if(i % 20 == 19){

                    session.flush();
                    session.clear();

                }

            }


            tx.commit();

            return true;


        }catch(Exception e){


            if(tx != null)
                tx.rollback();


            System.out.println(
                "Lỗi HoadonCTDao.insertBatch(): "
                + e.getMessage()
            );


            return false;
        }

    }

    

 // Cập nhật chi tiết hóa đơn
    public boolean update(HoadonCT hdct){

        Transaction tx = null;


        try(Session session =
                HibernateUtils
                .getSessionFactory()
                .openSession()){


            tx = session.beginTransaction();


            session.merge(hdct);


            tx.commit();


            return true;


        }catch(Exception e){


            if(tx != null)
                tx.rollback();


            System.out.println(
                "Lỗi HoadonCTDao.update(): "
                + e.getMessage()
            );


            return false;
        }

    }



    // Xóa chi tiết hóa đơn
    public boolean delete(int maHDCT){


        Transaction tx=null;


        try(Session session =
                HibernateUtils.getSessionFactory().openSession()){


            tx=session.beginTransaction();


            HoadonCT hdct =
                    session.find(
                            HoadonCT.class,
                            maHDCT
                    );


            if(hdct==null){

                tx.rollback();

                return false;
            }



            session.remove(hdct);


            tx.commit();


            return true;


        }catch(Exception e){


            if(tx!=null)
                tx.rollback();


            return false;
        }
    }



}