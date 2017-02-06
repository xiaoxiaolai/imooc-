package dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import entity.ThreadInfo;

/**
 * Created by xiao on 2016/8/30.
 */

public class ThreadInfoDao extends BaseDao<ThreadInfo,Integer> {
    public ThreadInfoDao(Context context) {
        super(context);
    }

    @Override
    public Dao<ThreadInfo, Integer> getDao() throws SQLException {
        return getHelper().getDao(ThreadInfo.class);
    }


}
//public class CashFlowDao extends BaseDao<CashFlow, Integer> {</p><p> public CashFlowDao(Context context) {
//    super(context);
//}</p><p> @Override
//public Dao<CashFlow, Integer> getDao() throws SQLException {
//    return getHelper().getDao(CashFlow.class);
//}