package it.cnr.ilc.clavius.manager;

//import it.cnr.ilc.talmud.HibernateUtil;
import it.cnr.ilc.clavius.domain.Account;
//import org.hibernate.Criteria;
//import org.hibernate.SQLQuery;
//import org.hibernate.criterion.Restrictions;

/**
 *
 * @author oakgen
 */
public class LoginManager {

    //private static final String AUTHENTICATE_SQL = "select * from Account where username = ':u' and password = upper(sha1(':p'))";

    public Account authenticate(String username, String password) {
//        String sql = AUTHENTICATE_SQL.replaceAll(":u", username).replaceAll(":p", password);
//        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql).addEntity(Account.class);
//        return (Account) query.uniqueResult();
        Account acc = null;
        if( username.equals("test")&&password.equals("test") ){
            acc = new Account();
            acc.setName("test");
        }
        
        return acc;
    }

//    public Account authenticate2(String username, String password) {
//        Criteria criteria = HibernateUtil.getSession().createCriteria(Account.class);
//        criteria.add(Restrictions.eq("username", username));
//        criteria.add(Restrictions.eq("password", password));
//        return (Account) criteria.uniqueResult();
//    }
}
